package vip.openpark.security.common.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vip.openpark.security.domain.UserDO;
import vip.openpark.security.service.PermissionService;
import vip.openpark.security.service.RoleService;
import vip.openpark.security.service.UserService;

import java.util.List;

/**
 * <div>
 *     接口 {@link UserDetailsService} 用于通过用户名查询用户信息及其权限;
 *     将获取到的用户信息及其权限，封装到 {@link UserDetails} 中
 * </div>
 *
 * @author anthony
 * @version 2024/1/25 13:48
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private PermissionService permissionService;
	
	/**
	 * 根据用户名查询用户信息及权限，封装到 {@link UserDetails} 中
	 *
	 * <div>
	 *     spring security 5.0（含）之前，加密器使用方式为
	 *     1:先声明成 PasswordEncoder bean，参考自定义配置类 {@link PasswordEncoderConfig}
	 *     1.1:自定义配置类型使用 {@link BCryptPasswordEncoder} 作为加密器
	 *     2:然后在注入使用
	 * </div>
	 * <div>
	 *     spring security 5.0 之后，加密器使用方式为：{@link PasswordEncoderFactories}，用于选择加密器
	 *     使用 {@code PasswordEncoderFactories.createDelegatingPasswordEncoder()} 来创建密码加密器
	 * </div>
	 *
	 * @param username the username identifying the user whose data is required.
	 * @return the user data required.
	 * @throws UsernameNotFoundException if the user could not be found.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("登录用户名：{}", username);
		
		// 根据用户名去数据库中查询用户信息包括密码
		UserDO userDO = userService.get(username);
		// 判断用户账号是否【存在】，如果不存在直接报错
		if (userDO == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		
		List<String> roles = roleService.selectByUserId(userDO.getId());
		List<String> permissions = permissionService.selectByRoles(roles);
		
		return new CustomGrantedAuthority(userDO, roles, permissions);
	}
}