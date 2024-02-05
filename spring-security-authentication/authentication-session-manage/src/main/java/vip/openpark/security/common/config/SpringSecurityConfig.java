package vip.openpark.security.common.config;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * <div>
 *     注解 {@link EnableWebSecurity} 标识是启用 Spring Security
 *     注解 {@link Configuration} 给 spring 容器标识该类是配置类
 * </div>
 *
 * @author anthony
 * @since 2024/1/25 23:34
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Resource
	private UserDetailsService userDetailsService;
	@Resource
	private PersistentTokenRepository persistentTokenRepository;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// 自定义验证码过滤器
			.addFilterBefore(new GraphCaptchaFilter(), UsernamePasswordAuthenticationFilter.class)
			// HTTP 授权处理
			.authorizeHttpRequests(
				authorizeHttpRequestsCustomizer ->
					authorizeHttpRequestsCustomizer
						// 只允许匿名用户访问的地址
						.requestMatchers(
							"/public/login.html", "/graphCaptcha/generate", "/login",
							"/public/loginFailure.html", "/public/logout.html",
							"/public/expiredUrl.html")
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
						// 登录成功后跳转的地址，true则表示总是指定页面，false表示调整用户登录之前的页面
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
			// 记住我
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
			// 会话管理
			.sessionManagement(
				sessionManagementCustomizer ->
					sessionManagementCustomizer
						.sessionFixation(
							SessionManagementConfigurer.SessionFixationConfigurer::migrateSession
						)
						// 会话并发控制
						.sessionConcurrency(
							sessionConcurrencyCustomizer ->
								sessionConcurrencyCustomizer
									// 最大会话数
									.maximumSessions(1)
									// 达到最大会话数，true禁止登录，false表示可以登录但是会剔除一个
									.maxSessionsPreventsLogin(false)
									// 如果用户尝试访问资源并且由于当前用户的会话过多而导致其会话已过期，则要重定向到的URL。（注意：需要在 spring security 认证中释放页面权限）
									.expiredUrl("/public/expiredUrl.html")
									// 会话过期处理在，不会主动触发，访问的时候会触发，这里演示打印日志（会覆盖上面 expiredUrl）
									.expiredSessionStrategy(event -> {
										SessionInformation sessionInformation = event.getSessionInformation();
										if (sessionInformation.isExpired()) {
											// session 过期
											log.info("session expired: {}", JSON.toJSONString(sessionInformation));
										}
									})
						)
			)
			// 禁用 CSRF 防护，这里先禁用，后续再开启
			.csrf(AbstractHttpConfigurer::disable);
		
		return http.build();
	}
}