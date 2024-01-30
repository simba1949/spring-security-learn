package vip.openpark.security.common.config;

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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

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
			// HTTP 授权处理
			.authorizeHttpRequests(
				(authorize) ->
					authorize
						// 允许匿名用户访问，不允许已登入用户访问
						.requestMatchers("/login")
						.anonymous()
						// 不管登入或者是未登入，都能访问
						.requestMatchers("/view/public/index.html")
						.permitAll()
						// 其他请求需要认证
						.anyRequest()
						.authenticated()
			
			)
			// 登入处理
			.formLogin(
				(httpSecurityFormLoginConfigurer) ->
					httpSecurityFormLoginConfigurer
						.loginPage("/view/public/login.html") // 登入页面【注意：这里需要配合 spring mvc 静态资源放行】
						.loginProcessingUrl("/login") // spring security 默认的登入处理地址
						.defaultSuccessUrl("/view/public/index.html", true) // 登入成功后重定向到首页
						.permitAll() // 允许匿名用户访问
			)
			// 登出处理
			.logout(
				(httpSecurityLogoutConfigurer) ->
					httpSecurityLogoutConfigurer
						.logoutUrl("/logout") // 默认的退出处理地址
						.logoutSuccessUrl("/view/public/logout.html") // 退出成功后重定向页面
						.deleteCookies("JSESSIONID") // 退出后删除 cookie
						.invalidateHttpSession(true) // 退出后销毁 session
						.permitAll()
			)
			// CSRF 处理
			.csrf(
				httpSecurityCsrfConfigurer ->
					// 哪些接口允许 CSRF，跨站请求伪造，英语：Cross-site request forgery
					httpSecurityCsrfConfigurer
						.ignoringRequestMatchers("/login", "/logout")
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // cookie 保存 csrf token
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