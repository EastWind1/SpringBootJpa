package test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import test.config.authentication.AuthenticateEntryPoint;
import test.config.authentication.AuthenticateFailHandler;
import test.config.authentication.AuthenticateSuccessHandler;
import test.config.authentication.TokenAuthenticateFilter;
import test.pojo.entity.User;
import test.service.UserService;
import test.util.JwtTokenUtils;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // 此处必须使用@Bean装配，否则会无限循环，导致栈溢出
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService();
    }

    @Autowired
    public AuthenticateSuccessHandler authenticateSuccessHandler;

    @Autowired
    public AuthenticateFailHandler authenticateFailHandler;

    @Autowired
    public AuthenticateEntryPoint authenticateEntryPoint;

    @Autowired
    public TokenAuthenticateFilter tokenAuthenticateFilter;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
        // 5.x以上版本必须配置密码配置类
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭csrf防护,cors防护
                .csrf().disable().cors()
                .and()
                .authorizeRequests()
                // 允许所求跨域前置请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 允许添加用户api访问
                .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                .antMatchers("/api/*").authenticated()
                .antMatchers("/userfiles/*").authenticated()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/api/login")
                    // 覆盖默认登陆后跳转原访问界面
                    .successHandler(authenticateSuccessHandler)
                    // 覆盖默认登陆失败后跳转登录界面
                    .failureHandler(authenticateFailHandler)
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .permitAll()
                .and()
                    // 覆盖默认访问未授权api跳转登录界面
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticateEntryPoint)
                .and()
                    // 添加Token验证，关闭默认Session验证
                    .addFilterBefore(tokenAuthenticateFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

}
