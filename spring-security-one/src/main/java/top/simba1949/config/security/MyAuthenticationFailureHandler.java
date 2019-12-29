package top.simba1949.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 自定义登录失败处理器，需要继承 SimpleUrlAuthenticationFailureHandler 或者实现 AuthenticationFailureHandler 接口
 * @AUTHOR Theodore
 * @DATE 2019/12/29 15:29
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败之后的处理逻辑");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("账号密码不正确，请重新登录");
    }
}
