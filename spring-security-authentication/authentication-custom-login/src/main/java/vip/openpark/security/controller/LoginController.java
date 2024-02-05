package vip.openpark.security.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author anthony
 * @version 2024/2/5 14:42
 */
@Slf4j
@Controller
@RequestMapping("loginController")
public class LoginController {
	@Resource
	private SecurityContextRepository securityContextRepository;
	@Resource
	private AuthenticationManager authenticationManager;
	@Resource
	private SecurityContextHolderStrategy securityContextHolderStrategy;
	
	@PostMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// 创建令牌
		UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
		// 认证
		Authentication authentication = authenticationManager.authenticate(token);
		// 创建上下文
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		// 设置上下文
		securityContextHolderStrategy.setContext(context);
		// 保存上下文
		securityContextRepository.saveContext(context, request, response);
	}
}