package vip.openpark.security.common.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author anthony
 * @since 2024/1/25 23:34
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Resource
	UserDetailsService userDetailsService;
	@Resource
	PersistentTokenRepository persistentTokenRepository;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// HTTP 授权处理
			.authorizeHttpRequests(
				authorizeHttpRequestsCustomizer ->
					authorizeHttpRequestsCustomizer
						// 允许匿名用户访问，不允许已登录的用户访问
						.requestMatchers("/public/login.html", "/graphCaptcha/generate", "/login")
						.anonymous()
						// 不管登入或者是未登入，都能访问
						.requestMatchers("/user/isRegister")
						.permitAll()
						// 其他请求需要认证
						.anyRequest()
						.authenticated()
			
			)
			.formLogin(
				httpSecurityFormLoginConfigurer ->
					httpSecurityFormLoginConfigurer
						// 登录页面
						.loginPage("/public/login.html")
						// 登录处理地址
						.loginProcessingUrl("/login")
						// 登录成功后跳转的地址，true则表示总是指定页面，false表示调整用户登录之前的页面
						.defaultSuccessUrl("/public/index.html", true)
				// 这里不要加 .permitAll()
				// 如果添加 permitAll() ，则会跳转到登录页面，而不是跳转到登录成功页面，这里登录页面允许匿名用户访问
				// .permitAll()
			)
			.rememberMe(
				rememberMeCustomizer ->
					rememberMeCustomizer
						// 前端传过来的记住我参数，默认是 remember-me
						.rememberMeParameter("remember-me")
						// 记住我 cookie 名称
						.rememberMeCookieName("remember-me")
						// 必要要指定登录信息
						.userDetailsService(userDetailsService)
						// 多长时间内自动登录（这里演示设置2分钟，2分钟内有效）
						.tokenValiditySeconds(2 * 60)
						.tokenRepository(persistentTokenRepository)
			)
			// 登出处理
			.logout(
				(httpSecurityLogoutConfigurer) ->
					httpSecurityLogoutConfigurer
						.logoutUrl("/logout") // 默认的退出处理地址
						.logoutSuccessUrl("/public/logout.html") // 退出成功后重定向页面
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
	
	@Bean
	public PasswordEncoder PasswordEncoder() {
		// spring security 5.0 （含）之前版本，使用方式：new BCryptPasswordEncoder();
		// return new BCryptPasswordEncoder();
		// spring security 5.0 以上版本，使用方式：PasswordEncoderFactories.createDelegatingPasswordEncoder()
		// spring security 5.0 以上版本，可以不用配置，默认这种方式
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}