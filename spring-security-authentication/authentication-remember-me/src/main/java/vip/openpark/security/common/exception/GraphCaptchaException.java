package vip.openpark.security.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <div>
 *     定义一个图形验证码异常
 * </div>
 *
 * @author anthony
 * @since 2024/2/3 15:02
 */
public class GraphCaptchaException extends AuthenticationException {
	private static final long serialVersionUID = 1447403919946594857L;
	
	public GraphCaptchaException(String msg) {
		super(msg);
	}
}