package test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.restart.FailureHandler;
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
import test.config.authentication.*;
import test.pojo.entity.User;
import test.service.UserService;
import test.util.JwtTokenUtils;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserService userService;

    @Autowired
    public AuthenticateSuccessHandler authenticateSuccessHandler;

    @Autowired
    public AuthenticateFailHandler authenticateFailHandler;

    @Autowired
    public AuthenticateEntryPoint authenticateEntryPoint;

    @Autowired
    public TokenAuthenticateFilter tokenAuthenticateFilter;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
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
                    .antMatchers("/api/socket").permitAll()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/userfiles/**").authenticated()
                .and()
                    .formLogin()
                    .permitAll()
                .and()
                    // 覆盖原有登录拦截器，改用json、form两种形式
                    .addFilterAt(formAndJsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    // 添加Token验证，关闭默认Session验证
                    .addFilterBefore(tokenAuthenticateFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    // 覆盖默认访问未授权api跳转登录界面
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticateEntryPoint)
                .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .permitAll();
    }

    @Bean
    public FormAndJsonAuthenticationFilter formAndJsonAuthenticationFilter() throws Exception {
        FormAndJsonAuthenticationFilter filter = new FormAndJsonAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticateSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticateFailHandler);
        filter.setFilterProcessesUrl("/api/login");

        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

}
