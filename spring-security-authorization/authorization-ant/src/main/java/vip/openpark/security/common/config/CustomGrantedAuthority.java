package vip.openpark.security.common.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import vip.openpark.security.domain.UserDO;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author anthony
 * @since 2024/2/5 20:53
 */
@Getter
public class CustomGrantedAuthority implements UserDetails {
	private static final long serialVersionUID = 1793192130366892978L;
	
	private final UserDO userDO;
	private final Set<GrantedAuthority> authorities;
	
	public CustomGrantedAuthority(UserDO userDO, Collection<String> roles, Collection<String> permissions) {
		this.userDO = userDO;
		this.authorities = new HashSet<>();
		
		// 添加角色信息
		if (null != roles && !roles.isEmpty()) {
			roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
		}
		// 添加权限信息
		if (null != permissions && !permissions.isEmpty()) {
			permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		// 这里只做演示，测试数据库存储的是明文，这里需要加密一下，实际生产中存放的是密文
		String password = userDO.getPassword();
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return passwordEncoder.encode(password);
	}
	
	@Override
	public String getUsername() {
		return userDO.getUsername();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
}