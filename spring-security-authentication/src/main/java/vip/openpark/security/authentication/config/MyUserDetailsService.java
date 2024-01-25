package vip.openpark.security.authentication.config;

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
import vip.openpark.security.authentication.domain.UserDO;
import vip.openpark.security.authentication.service.UserService;

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
		// 这里假设数据库中查询到的密码为：123456，这里使用的是明文密码，实际开发中需要加密
		// String plaintextPassword = "123456";
		UserDO userDO = userService.get(username);
		// 判断用户账号是否【存在】，如果不存在直接报错
		if (userDO == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		String plaintextPassword = userDO.getPassword();
		log.info("从数据库中查询的明文密码为：{}", plaintextPassword);
		// spring security 5.0 之后，使用方式为：DelegatingPasswordEncoder，用于选择加密器
		// 使用 {@code PasswordEncoderFactories.createDelegatingPasswordEncoder()} 来创建密码加密器
		// 这里要和配置的 {@code PasswordEncoder} 加密方式一致
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String encodePassword = passwordEncoder.encode(plaintextPassword);
		log.info("从数据库中查询的密文密码为：{}", encodePassword);
		
		// 根据用户名去数据查询用户的信息
		// 判断用户账号是否【启用】，true 表示启用，false 表示禁用
		boolean enabledFlag = true;
		// 判断用户账号是否【没有过期】，true 表示不过期，false 表示过期
		boolean accountNonExpiredFlag = true;
		// 判断用户账号密码是否【没有过期】，true 表示未过期，false 表示过期
		boolean credentialsNonExpiredFlag = true;
		// 判断用户账号是否【没有冻结】，true 表示没有冻结，false 表示已冻结
		boolean accountNonLockedFlag = true;
		
		// 第一个参数表示用户名，第二个参数表示加密后的密码，最后一个参数表示该用户拥有哪些角色（角色对应权限）
		return new User(
			username, encodePassword,
			enabledFlag, accountNonExpiredFlag, credentialsNonExpiredFlag, accountNonLockedFlag,
			AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
}