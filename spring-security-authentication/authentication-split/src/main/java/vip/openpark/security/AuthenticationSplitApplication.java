package vip.openpark.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author anthony
 * @since 2024/1/30 23:35
 */
@MapperScan("vip.openpark.security.mapper")
@SpringBootApplication
public class AuthenticationSplitApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationSplitApplication.class, args);
	}
}