package vip.openpark.security.authentication.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import vip.openpark.security.authentication.common.response.Response;
import vip.openpark.security.authentication.domain.UserDO;
import vip.openpark.security.authentication.service.UserService;

import java.util.List;

/**
 * @author anthony
 * @since 2024/1/25 20:40
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
	@Resource
	private UserService userService;
	
	@GetMapping("registerCount")
	public Response<Long> registerCount() {
		Long registerCount = userService.registerCount();
		return Response.success(registerCount);
	}
	
	@GetMapping("isRegister")
	public Response<Boolean> isRegister(@RequestParam("username") String username) {
		boolean isRegister = userService.isRegister(username);
		return Response.success(isRegister);
	}
	
	@GetMapping("all")
	public Response<List<UserDO>> all() {
		List<UserDO> all = userService.all();
		return Response.success(all);
	}
	
	@GetMapping("myName")
	public Response<String> getUser(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return Response.success(user.getUsername());
	}
}