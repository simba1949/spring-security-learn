package vip.openpark.security.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author anthony
 * @version 2024/1/26 16:17
 */
@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 第一个方法设置访问路径前缀，第二个方法设置资源路径
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
		registry.addResourceHandler("/view/**").addResourceLocations("classpath:/view/");
	}
	
	/**
	 * 跨域配置
	 *
	 * @param registry CorsRegistry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 允许跨域访问的路径
			.allowedOrigins("*") // 允许跨域访问的域名
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // 允许跨域访问的方法
			.allowedHeaders("*") // 允许跨域访问的请求头
			.maxAge(3600);
	}
}
