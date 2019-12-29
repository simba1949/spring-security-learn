package top.simba1949;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/26 17:14
 */

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "top.simba1949.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
