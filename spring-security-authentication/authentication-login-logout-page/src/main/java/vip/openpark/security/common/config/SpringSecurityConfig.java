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
						// 只允许匿名用户访问的地址
						.requestMatchers("/public/login.html", "/login", "/public/loginFailure.html", "/public/logout.html")
						.anonymous()
						// 匿名和登录用户都可以访问的地址
						.requestMatchers("/public/index.html")
						.permitAll()
						// 除上述外，所有请求都通过认证（除了spring security 内置的登录页面和登录接口）
						.anyRequest()
						.authenticated()
			)
			// 登录处理
			.formLogin(
				formLoginCustomizer ->
					formLoginCustomizer
						// 登录页面
						.loginPage("/public/login.html")
						// 登录接口
						.loginProcessingUrl("/login")
						// 登录成功后，跳转到首页
						.defaultSuccessUrl("/public/index.html", true)
						// 登录失败后，跳转到登录失败页面
						.failureUrl("/public/loginFailure.html")
			)
			// 登出处理
			.logout(
				logoutCustomizer ->
					logoutCustomizer
						// 登出接口
						.logoutUrl("/logout")
						// 登出成功后，跳转到登录页面
						.logoutSuccessUrl("/public/logout.html")
						// 删除 cookie
						.deleteCookies("JSESSIONID")
						// 清除 session
						.invalidateHttpSession(true)
						// 清除认证信息
						.clearAuthentication(true)
			)
			// 禁用 CSRF 防护，这里先禁用，后续再开启
			.csrf(AbstractHttpConfigurer::disable);
		
		return http.build();
	}
}