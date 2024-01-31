package vip.openpark.security.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author anthony
 * @version 2024/1/26 16:17
 */
@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {
	
	/**
	 * 跨域配置
	 *
	 * @param registry CorsRegistry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**") // 允许跨域访问的路径
			.allowedOriginPatterns("*") // 允许跨域访问的域名
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // 允许跨域访问的方法
			.allowedHeaders("*") // 允许跨域访问的请求头
			.exposedHeaders("*") // 允许跨域访问的响应头
			.allowCredentials(true) // 允许跨域访问的cookie
		;
	}
}