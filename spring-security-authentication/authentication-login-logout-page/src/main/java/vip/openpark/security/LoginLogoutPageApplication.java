package vip.openpark.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author anthony
 * @version 2024/2/1 9:31
 */
@MapperScan("vip.openpark.security.mapper")
@SpringBootApplication
public class LoginLogoutPageApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoginLogoutPageApplication.class, args);
	}
}