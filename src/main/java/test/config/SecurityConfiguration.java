package test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import test.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭csrf防护
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/*").authenticated()
                // 允许添加用户api访问
                .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/api/login")
                    // 覆盖默认登陆后跳转原访问界面
                    .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
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
                    });

    }

}
