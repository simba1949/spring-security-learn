package vip.openpark.security.common.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;

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
		http
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			// HTTP 授权处理
			.authorizeHttpRequests(
				(authorize) ->
					authorize
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
				(httpSecurityFormLoginConfigurer) ->
					httpSecurityFormLoginConfigurer
						.loginProcessingUrl("/login") // spring security 默认的登入处理地址
						// 登录成功的处理器
						.successHandler(new AuthenticationSuccessHandler() {
							@Override
							public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
								log.info("authentication = {}", JSON.toJSONString(authentication));
								log.info("Principal = {}", JSON.toJSONString(authentication.getPrincipal()));
								log.info("Credentials = {}", JSON.toJSONString(authentication.getCredentials()));
								log.info("Details = {}", JSON.toJSONString(authentication.getDetails()));
								log.info("Authorities = {}", JSON.toJSONString(authentication.getAuthorities()));
								
								response.setContentType("application/json;charset:utf-8;");
								response.getWriter().write("登录成功");
								response.getWriter().flush();
							}
						})
						// 登录失败的处理器
						.failureHandler(new AuthenticationFailureHandler() {
							@Override
							public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
								log.error("exception = {}", exception.getMessage(), exception);
								
								response.setContentType("application/json;charset:utf-8;");
								response.getWriter().write("登录失败");
								response.getWriter().flush();
							}
						})
						.permitAll() // 允许匿名用户访问
			)
			// 登出处理
			.logout(
				(httpSecurityLogoutConfigurer) ->
					httpSecurityLogoutConfigurer
						.logoutUrl("/logout") // 默认的退出处理地址
						.logoutSuccessHandler(new LogoutSuccessHandler() {
							@Override
							public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
								log.info("authentication = {}", JSON.toJSONString(authentication));
								log.info("Principal = {}", JSON.toJSONString(authentication.getPrincipal()));
								log.info("Credentials = {}", JSON.toJSONString(authentication.getCredentials()));
								log.info("Details = {}", JSON.toJSONString(authentication.getDetails()));
								log.info("Authorities = {}", JSON.toJSONString(authentication.getAuthorities()));
								
								response.setContentType("text/html;charset=UTF-8");
								response.getWriter().write("退出成功");
								response.getWriter().flush();
							}
						})
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
	
	/**
	 * 跨域配置
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
}