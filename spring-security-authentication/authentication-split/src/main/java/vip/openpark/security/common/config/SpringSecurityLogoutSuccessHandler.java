package vip.openpark.security.common.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * 注销成功的处理器
 *
 * @author anthony
 * @since 2024/1/31 20:41
 */
@Slf4j
public class SpringSecurityLogoutSuccessHandler implements LogoutSuccessHandler {
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		log.info("authentication = {}", JSON.toJSONString(authentication));
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("退出成功");
		response.getWriter().flush();
	}
}