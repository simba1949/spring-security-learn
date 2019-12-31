package top.simba1949.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义 SpringSecurity 认证流程
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/30 10:42
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录用户名：{}", username);
        // 根据用户名去数据库中查询用户信息包括密码
        if (username.equalsIgnoreCase("1234567890") || username.equalsIgnoreCase("1234567890@163.com")){
            username = "user";
        }
        String password = passwordEncoder.encode("123456");
        log.info("从数据库中查询的密码为：{}", password);

        // 根据用户名去数据查询用户的信息，判断用户是否启用，账号是否过期，密码是否过期，账号是否没有冻结
        // 用户是否启用
        boolean enabled = true;
        // 账号是否没有过期
        boolean accountNonExpired = true;
        // 密码是否过期
        boolean credentialsNonExpired = true;
        // 账号是否没有冻结
        boolean accountNonLocked = true;

        // 第一个参数表示用户名，第二个参数表示加密后的密码，最后一个个参数表示该用户拥有哪些权限
        return new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
            AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
