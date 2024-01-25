package vip.openpark.security.authentication.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}