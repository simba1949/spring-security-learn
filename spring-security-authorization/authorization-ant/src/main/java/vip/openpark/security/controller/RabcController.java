package vip.openpark.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anthony
 * @since 2024/2/5 21:37
 */
@RestController
public class RabcController {
	/**
	 * 角色
	 */
	@GetMapping("adminRole")
	public String adminRole() {
		return "adminRole";
	}
	
	@GetMapping("managerRole")
	public String managerRole() {
		return "managerRole";
	}
	
	@GetMapping("userRole")
	public String userRole() {
		return "userRole";
	}
	
	/**
	 * 菜单/路径
	 */
	@GetMapping("adminPermission")
	public String adminPermission() {
		return "adminPermission";
	}
	
	@GetMapping("managerPermission")
	public String managerPermission() {
		return "managerPermission";
	}
	
	@GetMapping("userPermission")
	public String userPermission() {
		return "userPermission";
	}
}