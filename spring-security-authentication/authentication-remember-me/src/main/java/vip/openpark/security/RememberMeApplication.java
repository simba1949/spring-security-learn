package vip.openpark.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author anthony
 * @since 2024/1/25 12:13
 */
@MapperScan("vip.openpark.security.mapper")
@SpringBootApplication
public class RememberMeApplication {
	public static void main(String[] args) {
		SpringApplication.run(RememberMeApplication.class, args);
	}
}