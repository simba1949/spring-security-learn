package vip.openpark.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author anthony
 * @since 2024/2/3 9:49
 */
@SpringBootApplication
@MapperScan("vip.openpark.security.mapper")
public class AuthenticationGraphCaptchaApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationGraphCaptchaApplication.class, args);
	}
}