package vip.openpark.security.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import vip.openpark.security.common.request.LoginRequest;
import vip.openpark.security.common.response.Response;
import vip.openpark.security.common.util.JwtUtils;

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
	
	@PostMapping("login")
	public Response<String> login(@RequestBody LoginRequest request) {
		log.info("请求登录入参：{}", request);
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
		Authentication authenticate = authenticationManager.authenticate(token);
		if (!authenticate.isAuthenticated()) {
			return Response.fail("E100000", "登录失败");
		}
		
		User user = (User) authenticate.getPrincipal();
		String jwt = JwtUtils.createToken(user.getUsername());
		
		return Response.success(jwt);
	}
}
