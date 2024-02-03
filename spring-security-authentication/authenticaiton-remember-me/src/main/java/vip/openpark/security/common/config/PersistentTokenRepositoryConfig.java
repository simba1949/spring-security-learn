package vip.openpark.security.common.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author anthony
 * @since 2024/2/3 19:14
 */
@Configuration
public class PersistentTokenRepositoryConfig {
	@Resource
	private DataSource dataSource;
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		// 启动时候自动创建 tokenRepository 需要的表结构，第一次启动时可以配置为true，以后不能配置为true
		// 或者进入 JdbcTokenRepositoryImpl 类，执行类中对应的SQL，下面一行设置为false或者删除即可
		jdbcTokenRepository.setCreateTableOnStartup(false);
		return jdbcTokenRepository;
	}
}