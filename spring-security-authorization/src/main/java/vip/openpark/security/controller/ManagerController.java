package vip.openpark.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anthony
 * @version 2024/2/5 16:48
 */
@RestController
@RequestMapping("manager")
public class ManagerController {
	
	@RequestMapping("all")
	public String all() {
		return "管理员";
	}
}