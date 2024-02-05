package vip.openpark.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author anthony
 * @version 2024/2/5 16:34
 */
@MapperScan("vip.openpark.security.mapper")
@SpringBootApplication
public class AuthorizationAntApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthorizationAntApplication.class, args);
	}
}