package vip.openpark.security.common.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * @author anthony
 * @since 2024/2/3 17:28
 */
@Component
public class CaptchaWebAuthenticationDetailsSource
	implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
	
	@Override
	public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new CaptchaWebAuthenticationDetails(context);
	}
}