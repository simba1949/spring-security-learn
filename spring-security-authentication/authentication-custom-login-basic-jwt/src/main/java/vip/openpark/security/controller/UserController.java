package vip.openpark.security.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vip.openpark.security.common.config.CustomUserDetails;
import vip.openpark.security.common.request.LoginRequest;
import vip.openpark.security.common.response.Response;
import vip.openpark.security.common.util.JwtUtils;
import vip.openpark.security.common.util.UserUtils;
import vip.openpark.security.domain.UserInfo;

import java.util.concurrent.TimeUnit;

/**
 * @author anthony
 * @since 2024/2/5 23:24
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
	@Resource
	private AuthenticationManager authenticationManager;
	@Resource
	private RedissonClient redissonClient;
	
	@PostMapping("login")
	public Response<String> login(@RequestBody LoginRequest request) {
		log.info("请求登录入参：{}", request);
		
		// 未认证的 Authentication
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
		// 认证后的 Authentication（实际是其子类 UsernamePasswordAuthenticationToken）
		Authentication authenticate = authenticationManager.authenticate(token);
		if (!authenticate.isAuthenticated()) {
			return Response.fail("E100000", "登录失败");
		}
		
		CustomUserDetails principal = (CustomUserDetails) authenticate.getPrincipal();
		UserInfo userInfo = UserUtils.getUser(principal);
		String jwt = JwtUtils.createToken(userInfo.getUsername());
		// 保存到 redis
		RBucket<UserInfo> bucket = redissonClient.getBucket(jwt);
		bucket.set(userInfo, 10L, TimeUnit.MINUTES);
		
		return Response.success(jwt);
	}
	
	/**
	 * 登出
	 *
	 * @param request 请求
	 * @return 响应
	 */
	@GetMapping("/logout")
	public Response<Void> logout(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (StrUtil.isNotBlank(authorization)) {
			redissonClient.getBucket(authorization).delete();
		}
		
		return Response.success();
	}
}