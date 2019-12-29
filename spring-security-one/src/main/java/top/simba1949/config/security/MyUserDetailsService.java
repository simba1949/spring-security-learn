package top.simba1949.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import top.simba1949.common.rbac.Role;
import top.simba1949.service.RoleService;
import top.simba1949.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义认证流程
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/28 17:01
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("请求登录的用户是：{}", username);
        // 根据用户名去数据库中查询用户信息
        top.simba1949.common.rbac.User user = userService.selectByUsername(username);
        // 查询用户的角色信息
        List<Role> roles = roleService.selectUserByUser(user.getId());
        // 封装角色信息
        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        // TODO 根据用户名去数据查询用户的信息，判断用户是否启用，账号是否过期，密码是否过期，账号是否没有冻结
        // 用户是否启用
        boolean enabled = true;
        // 账号是否没有过期
        boolean accountNonExpired = true;
        // 密码是否过期
        boolean credentialsNonExpired = true;
        // 账号是否没有冻结
        boolean accountNonLocked = true;

        // 第一个参数表示用户名，第二个参数表示加密后的密码，最后一个个参数表示该用户拥有哪些权限
        return new User(username, user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
