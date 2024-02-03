package vip.openpark.security.common.config;

import cn.hutool.core.lang.Assert;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vip.openpark.security.common.constant.SpringSecurityConstant;
import vip.openpark.security.common.exception.GraphCaptchaException;

import java.io.IOException;

/**
 * <div>
 *     <title>图形验证码过滤器</title>
 *     <div>
 *         1. 自定义图形验证码过滤器，继承 {@link OncePerRequestFilter}，每次请求都会调用
 *         2. 重写 {@link #doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)} 方法
 *     </div>
 * </div>
 *
 * @author anthony
 * @since 2024/2/3 14:58
 */
@Slf4j
public class GraphCaptchaFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
		// 如果是登录请求，进行校验
		String requestURI = request.getRequestURI();
		String method = request.getMethod();
		// 登录时进行图形验证码校验，如果其他uri也需要校验，可自行添加
		if ("/login".equalsIgnoreCase(requestURI) && "POST".equalsIgnoreCase(method)) {
			try {
				// 校验验证码核心逻辑
				validateCodeCore(request);
			} catch (AuthenticationException e) {
				// 身份认证失败，重定向到登录页（这里也可以指定失败处理器）
				response.sendRedirect("/public/login.html");
				// 认证失败无需往下走
				return;
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
	/**
	 * 校验图形验证码
	 *
	 * @param request 请求
	 */
	private void validateCodeCore(HttpServletRequest request) throws AuthenticationException {
		HttpSession session = request.getSession();
		String reqCode = null;
		
		try {
			// 获取请求中的验证码，如果获取不到直接报错
			reqCode = ServletRequestUtils.getStringParameter(request, "code");
			Assert.notBlank(reqCode);
		} catch (Exception e) {
			throw new GraphCaptchaException("获取不到图形验证码信息");
		}
		
		// 重新获取 session 中的验证码，获取不到直接报错
		String sessionValidateCode = (String) session.getAttribute(SpringSecurityConstant.SESSION_KEY);
		Assert.notBlank(sessionValidateCode, () -> new GraphCaptchaException("图形验证码已过期"));
		// request 中的图形验证码必须要和 session 中的图形验证码保持一致
		Assert.isTrue(sessionValidateCode.equalsIgnoreCase(reqCode), () -> new GraphCaptchaException("图形验证码不正确"));
		
		// 验证通过后，删除 session 中的验证码
		session.removeAttribute(SpringSecurityConstant.SESSION_KEY);
	}
}