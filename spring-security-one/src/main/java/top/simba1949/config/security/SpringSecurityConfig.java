package top.simba1949.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import top.simba1949.config.security.authorization.MyAccessDeniedHandler;

import javax.sql.DataSource;

/**
 * @EnableWebSecurity 开启 SpringSecurity
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/28 16:59
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    @Qualifier(value = "myUserDetailsService")
    private UserDetailsService userDetailsService;

    /**
     * 配置登录方式
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 认证：设置登录页面或者说是登录的路径
                // .loginPage("/user/loginPage")
                // 处理登录的url
                // .loginProcessingUrl("/user/login")
                // 自定义登录成功后的业务逻辑配置
                .successHandler(authenticationSuccessHandler)
                // 自定义登录失败后的业务逻辑配置
                .failureHandler(authenticationFailureHandler)
                .and()
                // 记住我的设置
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                // 设置过期时间
                .tokenValiditySeconds(3600)
                // 最终拿到 userDetailsService 去做登录
                .userDetailsService(userDetailsService)
                .and()
                // session 管理
                .sessionManagement()
                // session 失效的时候页面跳转地址，一般使用户重新登录认证
                .invalidSessionUrl("/index.html")
                // 设置同一用户 session 最大数量，控制session并发数
                .maximumSessions(1)
                // 当同一用户的 session 数量达到最大数时，阻止后面的用户登录
                // .maxSessionsPreventsLogin(true)
                // session 过期处理的业务逻辑，后一个登录会使前一个登录session过期
                .expiredSessionStrategy(new MyExpiredSessionStrategy())
                .and()
                .and()
                // 退出处理
                .logout()
                // 退出的url，SpringSecurity 默认退出url 是 /logout
                .logoutUrl("/logout")
                // 退出成功后的业务处理逻辑
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                // 浏览器退出后清空指定cookie
                .deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .anyRequest()
                // 权限表达式：rbacService.hasPermission 类.方法名，括号里面是请求参数
                .access("@rbacService.hasPermission(request,authentication)")
                .and()
                // 异常处理
                .exceptionHandling()
                // 无权限处理
                .accessDeniedHandler(new MyAccessDeniedHandler())
                .and()
                .csrf().disable();
    }

    /**
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 启动时候自动创建 tokenRepository 需要的表结构，第一次启动时可以配置为true，以后不能配置为true
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     * 配置加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
