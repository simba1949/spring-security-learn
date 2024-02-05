package vip.openpark.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasRole('ROLE_admin')")
	public String adminRole() {
		return "adminRole";
	}
	
	@GetMapping("managerRole")
	@PreAuthorize("hasRole('ROLE_manager')")
	public String managerRole() {
		return "managerRole";
	}
	
	@GetMapping("userRole")
	@PreAuthorize("hasRole('ROLE_user')")
	public String userRole() {
		return "userRole";
	}
	
	/**
	 * 菜单/路径
	 */
	@GetMapping("adminPermission")
	@PreAuthorize("hasAuthority('/adminPermission')")
	public String adminPermission() {
		return "adminPermission";
	}
	
	@GetMapping("managerPermission")
	@PreAuthorize("hasAuthority('/managerPermission')")
	public String managerPermission() {
		return "managerPermission";
	}
	
	@GetMapping("userPermission")
	@PreAuthorize("hasAuthority('/userPermission')")
	public String userPermission() {
		return "userPermission";
	}
}