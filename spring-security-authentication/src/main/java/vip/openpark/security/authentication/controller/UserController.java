package vip.openpark.security.authentication.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vip.openpark.security.authentication.common.Response;
import vip.openpark.security.authentication.common.request.user.UserLoginRequest;
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
	
	@GetMapping("all")
	public List<UserDO> all() {
		return userService.all();
	}
	
	@PostMapping("login")
	public Response login(@RequestBody UserLoginRequest request) {
		// AuthenticationManager authenticate 进行用户认证
		// 如果认证没有通过，返回错误信息
		// 如果认证通过，使用 userId 生成 JWT，并存储在 redis 中
		return new Response<>(true, "success", "登录成功", null);
	}
}