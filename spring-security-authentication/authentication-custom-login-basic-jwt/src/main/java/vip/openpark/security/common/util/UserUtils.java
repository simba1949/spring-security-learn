package vip.openpark.security.common.util;

import org.springframework.security.core.GrantedAuthority;
import vip.openpark.security.common.config.CustomUserDetails;
import vip.openpark.security.domain.UserDO;
import vip.openpark.security.domain.UserInfo;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author anthony
 * @version 2024/2/6 9:43
 */
public class UserUtils {
	
	private UserUtils() {
	}
	
	public static UserInfo getUser(CustomUserDetails customUserDetails) {
		if (null == customUserDetails || null == customUserDetails.getUserDO()) {
			return null;
		}
		
		UserDO userDO = customUserDetails.getUserDO();
		Set<String> authority = customUserDetails
			                        .getAuthorities()
			                        .stream()
			                        .map(GrantedAuthority::getAuthority)
			                        .collect(Collectors.toSet());
		
		return UserInfo.builder()
			       .id(userDO.getId())
			       .username(userDO.getUsername())
			       .authority(authority)
			       .build();
	}
}