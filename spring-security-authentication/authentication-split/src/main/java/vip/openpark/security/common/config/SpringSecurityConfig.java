package vip.openpark.security.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author anthony
 * @since 2024/1/25 23:34
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// 跨域配置
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("http://localhost:5173"); // 允许跨域的域名
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		http
			.cors(
				corsCustomizer ->
					corsCustomizer.configurationSource(source)
			)
			.csrf(AbstractHttpConfigurer::disable)
			// HTTP 授权处理
			.authorizeHttpRequests(
				authorizeHttpRequestsCustomizer ->
					authorizeHttpRequestsCustomizer
						// 允许匿名用户访问，不允许已登入用户访问
						.requestMatchers("/login")
						.anonymous()
						// 不管登入或者是未登入，都能访问
						.requestMatchers("/user/isRegister")
						.permitAll()
						// 其他请求需要认证
						.anyRequest()
						.authenticated()
			
			)
			// 登入处理
			.formLogin(
				httpSecurityFormLoginConfigurer ->
					httpSecurityFormLoginConfigurer
						.loginProcessingUrl("/login") // spring security 默认的登入处理地址
						// 登录成功的处理器（自定义的登录成功处理器）
						.successHandler(new SpringSecurityLoginSuccessHandler())
						// 登录失败的处理器（自定义的登录失败处理器）
						.failureHandler(new SpringSecurityLoginFailureHandler())
						.permitAll() // 允许匿名用户访问
			)
			// 登出处理
			.logout(
				httpSecurityLogoutConfigurer ->
					httpSecurityLogoutConfigurer
						.logoutUrl("/logout") // 默认的退出处理地址
						// 退出后的处理器（自定义的退出处理器）
						.logoutSuccessHandler(new SpringSecurityLogoutSuccessHandler())
						.deleteCookies("JSESSIONID") // 退出后删除 cookie
						.invalidateHttpSession(true) // 退出后销毁 session
						.permitAll()
			)
		;
		
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
	                                                   PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		
		ProviderManager providerManager = new ProviderManager(authenticationProvider);
		providerManager.setEraseCredentialsAfterAuthentication(false);
		
		return providerManager;
	}
}