package vip.openpark.security.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.openpark.security.common.response.Response;

import java.util.List;

/**
 * @author anthony
 * @version 2024/2/5 11:16
 */
@RestController
@RequestMapping("sessionManager")
public class SessionManagerController {
	@Resource
	private SessionRegistry sessionRegistry;
	
	@GetMapping("all")
	public Response<List<SessionInformation>> all() {
		// 获取所有用户
		List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		// 获取所有用户未失效的 session
		List<SessionInformation> sessionInformationList =
			allPrincipals.stream()
				.map(principal -> {
					// 获取该用户所有未失效的 session
					return sessionRegistry.getAllSessions(principal, false);
				})
				.flatMap(List::stream)
				.toList();
		
		return Response.success(sessionInformationList);
	}
	
	/**
	 * 通过用户名剔除登录用户
	 *
	 * @param username 用户名
	 * @return 结果字符串
	 */
	@GetMapping("/cull")
	public String cull(@RequestParam("username") String username) {
		if (StrUtil.isBlank(username)) {
			return "无用户信息";
		}
		
		// 获取所有用户
		List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		for (Object principal : allPrincipals) {
			User user = (User) principal;
			// 判断是否未需要剔除的用户
			if (!username.equalsIgnoreCase(user.getUsername())) {
				continue;
			}
			
			// 获取该用户所有会话
			List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principal, true);
			for (SessionInformation sessionInformation : allSessions) {
				if (sessionInformation != null) {
					// 移除 session
					sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
				}
			}
		}
		return username + "已下线";
	}
}