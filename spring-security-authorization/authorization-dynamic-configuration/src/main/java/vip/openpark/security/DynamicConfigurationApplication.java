package vip.openpark.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author anthony
 * @since 2024/2/5 23:14
 */
@MapperScan("vip.openpark.security.mapper")
@SpringBootApplication
public class DynamicConfigurationApplication {
	public static void main(String[] args) {
		SpringApplication.run(DynamicConfigurationApplication.class, args);
	}
}