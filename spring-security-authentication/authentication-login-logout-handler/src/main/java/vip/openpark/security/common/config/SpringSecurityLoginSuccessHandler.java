package vip.openpark.security.common.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * 登录成功的处理器
 *
 * @author anthony
 * @since 2024/1/31 20:38
 */
@Slf4j
public class SpringSecurityLoginSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		log.info("登录成功");
		log.info("authentication = {}", JSON.toJSONString(authentication));
		log.info("Principal = {}", JSON.toJSONString(authentication.getPrincipal()));
		log.info("Credentials = {}", JSON.toJSONString(authentication.getCredentials()));
		log.info("Details = {}", JSON.toJSONString(authentication.getDetails()));
		log.info("Authorities = {}", JSON.toJSONString(authentication.getAuthorities()));
		
		response.setContentType("text/html;charset=utf-8;");
		response.getWriter().write("登录成功");
		response.getWriter().flush();
	}
}