package vip.openpark.security.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author anthony
 * @since 2024/1/25 23:34
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) ->
				authorize
					// 允许匿名用户访问，不允许已登入用户访问
					.requestMatchers("/user/login", "/login", "/logout", "/user/logout")
					.anonymous()
					//不管登入,不登入 都能访问
					.requestMatchers("/user/login", "/login", "/logout", "/user/logout")
					.permitAll()
					.anyRequest()
					.permitAll()
			)
			.csrf(httpSecurityCsrfConfigurer ->
				// 哪些接口允许 CSRF，跨站请求伪造，英语：Cross-site request forgery
				httpSecurityCsrfConfigurer.ignoringRequestMatchers("/user/login", "/login", "/logout", "/user/logout")
			);
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