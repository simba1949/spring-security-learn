package vip.openpark.security.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import vip.openpark.security.common.exception.GraphCaptchaException;

/**
 * @author anthony
 * @since 2024/2/3 17:20
 */
@Slf4j
public class CaptchaAuthenticationProvider extends DaoAuthenticationProvider {
	
	public CaptchaAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.setUserDetailsService(userDetailsService);
		this.setPasswordEncoder(passwordEncoder);
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		CaptchaWebAuthenticationDetails details = (CaptchaWebAuthenticationDetails) authentication.getDetails();
		// 如果验证码错误，直接抛出异常
		if (!details.isGraphCaptchaRightFlag()) {
			throw new GraphCaptchaException("验证码错误");
		}
		
		super.additionalAuthenticationChecks(userDetails, authentication);
	}
}
