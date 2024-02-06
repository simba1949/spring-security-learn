package vip.openpark.security.domain;

import cn.hutool.core.collection.CollUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author anthony
 * @version 2024/2/6 9:53
 */
@Getter
@Setter
@Builder
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private Set<String> authority;
	
	private transient Set<GrantedAuthority> grantedAuthorities;
	
	public Set<GrantedAuthority> getGrantedAuthorities() {
		if (CollUtil.isNotEmpty(grantedAuthorities)) {
			return grantedAuthorities;
		}
		
		if (CollUtil.isNotEmpty(authority)) {
			this.grantedAuthorities =
				authority
					.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toSet());
		}
		
		return Collections.emptySet();
	}
	
}