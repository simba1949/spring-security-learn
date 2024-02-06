package vip.openpark.security.common.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author anthony
 * @since 2024/2/5 23:24
 */
@Getter
@Setter
public class LoginRequest implements Serializable {
	private static final long serialVersionUID = -3092009648329067680L;
	
	private String username;
	private String password;
}