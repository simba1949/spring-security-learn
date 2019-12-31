package top.simba1949.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import top.simba1949.config.security.account.AccountAuthenticationSecurityConfig;
import top.simba1949.config.security.authorization.MyAccessDeniedHandler;
import top.simba1949.filter.ValidateCodeFilter;

import javax.sql.DataSource;

/**
 * @EnableWebSecurity 开启使用 SpringSecurity
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/30 9:49
 */
@EnableWebSecurity
public class SpringSecurityCoreConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    @Qualifier("myLogoutSuccessHandler")
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private AccountAuthenticationSecurityConfig accountAuthenticationSecurityConfig;
    /**
     * 注入自定义认证流程
     */
    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    /**
     * 定义 token 存储方式，示例使用功能 <b>数据库存储</b>
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 启动时候自动创建 tokenRepository 需要的表结构，第一次启动时可以配置为true，以后不能配置为true
        // 或者进入 JdbcTokenRepositoryImpl 类，执行类中对应的SQL，下面一行设置为false或者删除即可
        tokenRepository.setCreateTableOnStartup(false);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 图形验证码过滤器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();

        http
            // 图形验证码过滤器要在 UsernamePasswordAuthenticationFilter 之前执行
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            // form 表单登录
            .formLogin()
                // 自定义登录页面
                .loginPage("/login.html")
                // 自定义处理登录的uri，但是处理还是由SpringSecurity的UsernamePasswordAuthenticationFilter 进行获取用户名和密码，
                // 交给 MyUserDetailsService处理，这里只是个性化定制
                 .loginProcessingUrl("/user/login")
                // 配置自定义登录成功后的业务处理逻辑
                // .successHandler(new MyAuthenticationSuccessHandler())
                // 和上面直接 new 同样效果，不推荐使用 new，充分利用 spring 容器
                .successHandler(authenticationSuccessHandler)
                // 配置自定义认证失败后的业务处理逻辑
                .failureHandler(authenticationFailureHandler)
            .and()
                // 记住我配置：其实remember-me功能是将token存储起来，
                // 在未过期时，用户打开浏览器 remember-me 的 cookie还存在，后台自动捕捉到并登录，只是用户无感知而已
                .rememberMe()
                // 设置 token 存储位置
                .tokenRepository(persistentTokenRepository())
                // 设置 token 过期时间
                .tokenValiditySeconds(60)
                // 设置自定义认证流程，记住我时最终拿到对应token去userDetailsService做认证
                .userDetailsService(userDetailsService)
            .and()
                .logout()
                // 自定义退出的url
                .logoutUrl("/user/logout")
                // 退出成功后，访问的地址
                // .logoutSuccessUrl("/logout.html")
                // 配置成功退出后的业务逻辑
                .logoutSuccessHandler(logoutSuccessHandler)
                // 浏览器退出后清空指定cookie
                .deleteCookies("SESSION")
            .and()
                // session 管理配置
                .sessionManagement()
                // 会话失效时，跳转url和业务逻辑不能工共存
                // session 失效的时候跳转url路径
                .invalidSessionUrl("/session-expired.html")
                // 设置同一用户 session 最大数量，控制session并发数
                .maximumSessions(1)
                // 当会话过期的业务处理逻辑
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                // 当同一用户的 session 数量达到最大数时，阻止后面的用户登录
                //.maxSessionsPreventsLogin(true)
            .and()
            .and()
                // 设置基于 HttpServletRequest 请求配置
                .authorizeRequests()
                // 匹配 antMatchers 里的对应 uri，直接放行，防止进入登录页面不断重定向
                .antMatchers("/login.html", "/user/login", "/user/logout", "/logout.html",
                    "/logout-redirect.html", "/session-expired.html","/img/validate/create",
                    "/account/send-code"
                ).permitAll()
                .anyRequest()
                // 权限表达式：rbacService.hasPermission 类.方法名，括号里面是请求参数
                .access("@rbacService.hasPermission(request, authentication)")
                .and()
                // 异常处理
                .exceptionHandling()
                // 无权限处理
                .accessDeniedHandler(new MyAccessDeniedHandler())
            .and()
                // 禁用跨站请求防护
                .csrf()
                .disable()
                // 添加自定义短信邮箱认证的配置
                .apply(accountAuthenticationSecurityConfig)
        ;
    }

    /**
     * 配置加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(16);
    }
}