package test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsUtils;
import test.pojo.entity.User;
import test.service.UserService;
import test.util.JwtTokenUtils;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

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
                    .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                        User user = (User) authentication.getPrincipal();
                        String token = JwtTokenUtils.createToken(user.getUsername(), false);
                        httpServletResponse.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"status\": true,\"msg\": \"登录成功\"}");
                        out.flush();
                        out.close();
                    })
                    // 覆盖默认登陆失败后跳转登录界面
                    .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"status\": false,\"msg\": \"登录失败 - "+ e.getMessage() +"\"}");
                        out.flush();
                        out.close();
                    })
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .permitAll()
                .and()
                    // 覆盖默认访问未授权api跳转登录界面
                    .exceptionHandling()
                    .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                        httpServletResponse.setStatus(401);
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"status\": false,\"msg\": \"请求失败 - "+ e.getMessage() +"\"}");
                        out.flush();
                        out.close();
                    })
                .and()
                    // TODO: 添加TOKEN验证
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

}
