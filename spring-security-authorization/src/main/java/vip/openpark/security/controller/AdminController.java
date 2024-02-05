package vip.openpark.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anthony
 * @version 2024/2/5 16:39
 */
@RestController
@RequestMapping("admin")
public class AdminController {
	
	@GetMapping("all")
	public String all() {
		return "超级管理员";
	}
}