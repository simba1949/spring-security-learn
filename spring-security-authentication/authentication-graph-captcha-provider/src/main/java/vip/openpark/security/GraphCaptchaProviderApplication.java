package vip.openpark.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author anthony
 * @since 2024/2/3 17:03
 */
@SpringBootApplication
@MapperScan("vip.openpark.security.mapper")
public class GraphCaptchaProviderApplication {
	public static void main(String[] args) {
		SpringApplication.run(GraphCaptchaProviderApplication.class, args);
	}
}