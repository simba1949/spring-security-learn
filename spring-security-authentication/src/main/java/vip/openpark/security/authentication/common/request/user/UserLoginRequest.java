package vip.openpark.security.authentication.common.request.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author anthony
 * @since 2024/1/25 23:13
 */
@Setter
@Getter
public class UserLoginRequest implements Serializable {
	@Serial
	private static final long serialVersionUID = 2407475987654023453L;
	
	private String username;
	private String password;
}