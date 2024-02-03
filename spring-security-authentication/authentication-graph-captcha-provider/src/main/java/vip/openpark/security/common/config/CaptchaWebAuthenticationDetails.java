package vip.openpark.security.common.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import vip.openpark.security.common.constant.SpringSecurityConstant;

/**
 * @author anthony
 * @since 2024/2/3 17:22
 */
@Getter
@Setter
public class CaptchaWebAuthenticationDetails extends WebAuthenticationDetails {
	private static final long serialVersionUID = -8589467891648369179L;
	
	private boolean graphCaptchaRightFlag;
	
	public CaptchaWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		// 获取请求中的验证码
		String requestCode = request.getParameter("code");
		// 获取session中的验证码
		HttpSession session = request.getSession();
		String sessionCode = (String) session.getAttribute(SpringSecurityConstant.SESSION_KEY);
		// 校验请求中的验证码是否和 session 中的验证码一致
		if (null != sessionCode && !sessionCode.isEmpty()) {
			session.removeAttribute(SpringSecurityConstant.SESSION_KEY);
			// 如果一致，则设置验证码正确
			if (sessionCode.equalsIgnoreCase(requestCode)) {
				this.graphCaptchaRightFlag = true;
			}
		}
	}
}