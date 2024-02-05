package vip.openpark.security.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

/**
 * @author anthony
 * @version 2024/2/5 11:16
 */
@Configuration
public class SessionRegistryConfig {
	
	/**
	 * 通过 SessionRegistry 可以获取到当前所有以及登录的用户
	 * 根据登录的用户获取他的session信息
	 *
	 * @return SessionRegistry
	 */
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
}