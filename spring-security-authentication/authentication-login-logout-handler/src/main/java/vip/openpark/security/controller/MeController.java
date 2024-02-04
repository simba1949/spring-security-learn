package vip.openpark.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <title>Authentication 整体结构说明</title>
 * <div>
 * {
 *     "authorities":[],
 *     "details":{
 *         "remoteAddress":"0:0:0:0:0:0:0:1",
 *         "sessionId":"5102BE411CF314EE645CBF5F94D5227F"
 *     },
 *     "authenticated":true,
 *     "principal":{
 *         "password":null,
 *         "username":"anthony",
 *         "authorities":[],
 *         "accountNonExpired":true,
 *         "accountNonLocked":true,
 *         "credentialsNonExpired":true,
 *         "enabled":true
 *     },
 *     "credentials":null,
 *     "name":"anthony"
 * }
 * </div>
 *
 * @author anthony
 * @version 2024/2/1 9:32
 */
@Slf4j
@RestController
@RequestMapping("me")
public class MeController {
	
	@GetMapping("0")
	public Authentication authentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@GetMapping("1")
	public Authentication authentication(Authentication authentication) {
		return authentication;
	}
	
	@GetMapping("2")
	public UserDetails authentication(@AuthenticationPrincipal UserDetails userDetails) {
		// @AuthenticationPrincipal 是 Spring Security 的注解，用于获取当前登录用户的信息
		return userDetails;
	}
}