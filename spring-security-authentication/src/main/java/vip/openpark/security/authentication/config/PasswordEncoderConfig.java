package vip.openpark.security.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <div>
 *     spring security 5.0（含）之前密码加密配置
 *     使用 {@link Configuration} 配置 {@link PasswordEncoder}
 * </div>
 *
 * @author anthony
 * @version 2024/1/25 15:31
 */
//@Configuration
public class PasswordEncoderConfig {
	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}