package top.simba1949.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.simba1949.config.security.MyAuthenticationFailureHandler;
import top.simba1949.controller.ImgValidateController;
import top.simba1949.exception.ValidateException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/30 21:45
 */
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {

    private final AuthenticationFailureHandler authenticationFailureHandler = new MyAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("验证码过滤器");
        // 如果是登录请求，进行校验
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        // 登录时进行图形验证码校验，如果其他uri也需要校验，可自行添加
        if ("/user/login".equalsIgnoreCase(requestURI) && "POST".equalsIgnoreCase(method)){
            try {
                // 校验验证码
                String reqKey = "code";
                String sessionKey = ImgValidateController.SESSION_KEY;
                validateCode(request, reqKey, sessionKey);
            } catch (AuthenticationException e) {
                // 身份认证失败，由失败处理器进行处理
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                // 认证失败无需往下走
                return;
            }
        }

        // 用户账号登录(手机或者Email)
        if ("/user/account".equalsIgnoreCase(requestURI) && "POST".equalsIgnoreCase(method)){
            try {
                String account = ServletRequestUtils.getStringParameter(request, "account");
                // 校验验证码
                String reqKey = "code";
                String sessionKey = account;
                validateCode(request, reqKey, sessionKey);
            } catch (AuthenticationException e) {
                // 身份认证失败，由失败处理器进行处理
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                // 认证失败无需往下走
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 校验图形验证码
     * @param request
     */
    private void validateCode(HttpServletRequest request, String reqKey, String sessionKey) throws AuthenticationException {
        HttpSession session = request.getSession();
        String sessionValidateCode = null;
        String reqCode = null;

        try {
            reqCode = ServletRequestUtils.getStringParameter(request, reqKey);
        } catch (ServletRequestBindingException e) {
            throw new ValidateException("the error is what can't get code");
        }

        if (StringUtils.isEmpty(reqCode)){
            throw new ValidateException("can't get code");
        }

        try {
            sessionValidateCode = (String) session.getAttribute(sessionKey);
        } catch (Exception e) {
            throw new ValidateException("the code is expired");
        }

        if (StringUtils.isEmpty(sessionValidateCode)){
            throw new ValidateException("the code is expired");
        }

        if (!reqCode.equalsIgnoreCase(sessionValidateCode)){
            throw new ValidateException("the code doesn't right");
        }

        // 验证通过后，删除 session 中的验证码
        session.removeAttribute(sessionKey);
    }
}
