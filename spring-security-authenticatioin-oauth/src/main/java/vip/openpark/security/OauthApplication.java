package vip.openpark.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author anthony
 * @since 2024/2/4 22:08
 */
@SpringBootApplication
@MapperScan("vip.openpark.security.mapper")
public class OauthApplication {
	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
	}
}