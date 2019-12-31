package top.simba1949.config.security.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/31 8:04
 */
@Configuration
public class AccountAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 配置过滤器
        AccountAuthenticationFilter accountAuthenticationFilter = new AccountAuthenticationFilter();
        accountAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        accountAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        accountAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        // 配置自定义的 AuthenticationProvider
        AccountAuthenticationProvider accountAuthenticationProvider = new AccountAuthenticationProvider();
        accountAuthenticationProvider.setUserDetailsService(userDetailsService);

        // 将自定义的 过滤器和 Provider 配置到 SpringSecurity 过滤器链中
        http.authenticationProvider(accountAuthenticationProvider)
            .addFilterAfter(accountAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
