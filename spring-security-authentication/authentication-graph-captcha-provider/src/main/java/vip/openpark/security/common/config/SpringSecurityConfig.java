package vip.openpark.security.common.config;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * @author anthony
 * @since 2024/1/25 23:34
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Resource
	private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
	@Resource
	private UserDetailsService userDetailsService;
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		CaptchaAuthenticationProvider captchaAuthenticationProvider =
			new CaptchaAuthenticationProvider(userDetailsService, PasswordEncoderFactories.createDelegatingPasswordEncoder());
		ProviderManager providerManager = new ProviderManager(captchaAuthenticationProvider);
		
		http
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
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
						.authenticationDetailsSource(authenticationDetailsSource)
				// 这里不要加 .permitAll()
				// 如果添加 permitAll() ，则会跳转到登录页面，而不是跳转到登录成功页面，这里登录页面允许匿名用户访问
				// .permitAll()
			)
			// 自定义登录过滤器
			// 登出处理
			.logout(
				httpSecurityLogoutConfigurer ->
					httpSecurityLogoutConfigurer
						.logoutUrl("/logout") // 默认的退出处理地址
						.deleteCookies("JSESSIONID") // 退出后删除 cookie
						// 登出成功后跳转的地址
						.logoutSuccessUrl("/public/login.html")
						.invalidateHttpSession(true) // 退出后销毁 session
			)
			// 自定义认证管理器
			.authenticationManager(providerManager)
		;
		
		return http.build();
	}
	
	/**
	 * 返回一个密码加密器
	 * 这里要和数据库中用户密码的加密算法保持一致
	 *
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder PasswordEncoder() {
		// spring security 5.0 （含）之前版本，使用方式：new BCryptPasswordEncoder();
		// return new BCryptPasswordEncoder();
		// spring security 5.0 以上版本，使用方式：PasswordEncoderFactories.createDelegatingPasswordEncoder()
		// spring security 5.0 以上版本，可以不用配置，默认这种方式
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}