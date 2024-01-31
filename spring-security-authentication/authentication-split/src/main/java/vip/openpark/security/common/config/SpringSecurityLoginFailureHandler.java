package vip.openpark.security.common.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * 登录失败的处理器
 *
 * @author anthony
 * @since 2024/1/31 20:40
 */
@Slf4j
public class SpringSecurityLoginFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.error("exception = {}", exception.getMessage(), exception);
		
		response.setContentType("text/html;charset=utf-8;");
		response.getWriter().write("登录失败");
		response.getWriter().flush();
	}
}