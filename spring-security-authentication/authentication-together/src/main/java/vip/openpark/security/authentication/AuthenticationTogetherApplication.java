package vip.openpark.security.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <div>
 *     <title>前后端不分离的认证</title>
 * </div>
 *
 * @author anthony
 * @since 2024/1/25 12:13
 */
@MapperScan("vip.openpark.security.authentication.mapper")
@SpringBootApplication
public class AuthenticationTogetherApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationTogetherApplication.class, args);
	}
}