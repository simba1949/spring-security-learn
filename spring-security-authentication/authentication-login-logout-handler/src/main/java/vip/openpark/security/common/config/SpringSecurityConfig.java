package vip.openpark.security.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <div>
 *     注解 {@link EnableWebSecurity} 标识是启用 Spring Security
 *     注解 {@link Configuration} 给 spring 容器标识该类是配置类
 * </div>
 *
 * @author anthony
 * @since 2024/1/25 23:34
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// HTTP 授权处理
			.authorizeHttpRequests(
				authorizeHttpRequestsCustomizer ->
					authorizeHttpRequestsCustomizer
						// 所有请求都通过认证（除了spring security 内置的登录页面和登录接口）
						.anyRequest()
						.authenticated()
			)
			// 登录处理
			.formLogin(
				formLoginCustomizer ->
					formLoginCustomizer
						// 登录接口
						.loginProcessingUrl("/login")
						// 登录成功的处理器（自定义的登录成功处理器）
						.successHandler(new SpringSecurityLoginSuccessHandler())
						// 登录失败处理器（自定义的登录失败处理器）
						.failureHandler(new SpringSecurityLoginFailureHandler())
			)
			// 登出处理
			.logout(
				logoutCustomizer ->
					logoutCustomizer
						// 登出接口
						.logoutUrl("/logout")
						// 登出成功处理器（自定义的登出成功处理器）
						.logoutSuccessHandler(new SpringSecurityLogoutSuccessHandler())
			)
			// 禁用 CSRF 防护，这里先禁用，后续再开启
			.csrf(AbstractHttpConfigurer::disable);
		
		return http.build();
	}
}