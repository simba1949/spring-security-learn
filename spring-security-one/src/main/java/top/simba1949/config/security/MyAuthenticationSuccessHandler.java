package top.simba1949.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功处理器
 * 需要继承 SavedRequestAwareAuthenticationSuccessHandler 类或者实现 AuthenticationSuccessHandler 接口
 * @AUTHOR Theodore
 * @DATE 2019/12/29 15:24
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("登录成功之后的处理逻辑");
        response.setContentType("application/json;charset=utf-8");
        // 登录成功跳转到主页面
        response.sendRedirect("/index.html");
    }
}
