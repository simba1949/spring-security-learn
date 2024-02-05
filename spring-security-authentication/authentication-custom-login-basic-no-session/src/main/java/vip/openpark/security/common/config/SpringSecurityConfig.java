package vip.openpark.security.common.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author anthony
 * @since 2024/2/5 23:16
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Resource
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			// HTTP 授权处理
			.authorizeHttpRequests(
				authorizeHttpRequestsCustomizer ->
					authorizeHttpRequestsCustomizer
						// 只允许匿名用户访问的地址
						.requestMatchers("/user/login")
						.anonymous()
						// 匿名和登录用户都可以访问的地址
						.requestMatchers("/ping")
						.permitAll()
						// 除上述外，所有请求都通过认证（除了spring security 内置的登录页面和登录接口）
						.anyRequest()
						.authenticated()
			)
			// 会话管理
			.sessionManagement(
				sessionManagementCustomizer ->
					sessionManagementCustomizer
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			// 禁用 CSRF 防护，这里先禁用，后续再开启
			.csrf(AbstractHttpConfigurer::disable);
		
		return http.build();
	}
}
