package test.config.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import test.pojo.entity.User;
import test.util.JwtTokenUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录成功后处理
 */
@Component
public class AuthenticateSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        String token = JwtTokenUtils.createToken(user.getUsername(), false);
        httpServletResponse.setHeader(JwtTokenUtils.TOKEN_HEADER, JwtTokenUtils.TOKEN_PREFIX + token);
        // 设置跨域状态下允许前端js获取认证请求头，便于前端储存
        httpServletResponse.setHeader("Access-Control-Expose-Headers", JwtTokenUtils.TOKEN_HEADER);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.write("{\"status\": true,\"msg\": \"登录成功\"}");
        out.flush();
        out.close();
    }
}
