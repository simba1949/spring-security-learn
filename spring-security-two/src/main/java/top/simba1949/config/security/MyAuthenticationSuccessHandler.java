package top.simba1949.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证成功后业务处理逻辑
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/30 14:23
 */
@Slf4j
@Component(value = "authenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * HttpSessionRequestCache 存储用户上次访问的url
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 记录用户登录ip、时间等或者其他业务处理逻辑
        log.info("用户登录成功后的业务处理逻辑");
        // 自己获取用户上一次登录url，然后实行跳转
        // 和调用父类方法同样效果哈
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = savedRequest.getRedirectUrl();
        if (null == targetUrl){
            targetUrl = "/index.html";
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
        // 调用父类的方法，跳转到用户上次登录url中
        // super.onAuthenticationSuccess(request, response, authentication);
    }
}
