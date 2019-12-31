package top.simba1949.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出成功后业务逻辑
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/30 15:33
 */
@Slf4j
@Component
public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    /**
     * Spring 封装的重定向
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 比如记录日志，根据自己项目进行业务
        log.info("用户已经成功退出系统，记录日志");
        // 使用 RedirectStrategy 自定义跳转页面
        String logoutRedirectUrl = "/logout-redirect.html";
        redirectStrategy.sendRedirect(request, response, logoutRedirectUrl);
        // SpringSecurity 默认进入 '/logout.html'
        // super.onLogoutSuccess(request, response, authentication);
    }
}
