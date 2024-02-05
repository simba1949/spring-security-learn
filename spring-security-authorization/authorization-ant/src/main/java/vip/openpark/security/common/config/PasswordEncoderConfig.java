package vip.openpark.security.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <div>
 *     <title>使用 {@link Configuration} 配置 {@link PasswordEncoder}</title>
 *     <li>
 *         spring security 5.0 （含）之前版本，使用方式：new BCryptPasswordEncoder();
 *         {@link BCryptPasswordEncoder}
 *     </li>
 *     <li>
 *         spring security 5.0 以上版本，使用方式：PasswordEncoderFactories.createDelegatingPasswordEncoder()
 *         {@link PasswordEncoderFactories}
 *         spring security 5.0 以上版本，可以不用配置，默认这种方式
 *     </li>
 * </div>
 *
 * @author anthony
 * @version 2024/1/25 15:31
 */
@Configuration
public class PasswordEncoderConfig {
	/**
	 * 返回一个密码加密器
	 * 这里要和数据库中用户密码的加密算法保持一致
	 *
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder PasswordEncoder() {
		// spring security 5.0 （含）之前版本，使用方式：new BCryptPasswordEncoder();
		// return new BCryptPasswordEncoder();
		// spring security 5.0 以上版本，使用方式：PasswordEncoderFactories.createDelegatingPasswordEncoder()
		// spring security 5.0 以上版本，可以不用配置，默认这种方式
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}