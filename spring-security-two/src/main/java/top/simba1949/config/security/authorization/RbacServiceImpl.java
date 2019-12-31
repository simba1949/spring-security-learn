package top.simba1949.config.security.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/31 10:07
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // 判断结果
        boolean hasPermission = false;

        Object principal = authentication.getPrincipal();
        // 获取用户是否登录
        if (principal instanceof UserDetails || principal instanceof WebAuthenticationDetails){
            // 获取用户名
            String username = ((UserDetails) principal).getUsername();
            // 从数据库中读取用户所拥有权权限的url
            Set<String> urls = new HashSet<>();
            urls.add("/rbac/0");
            urls.add("/rbac/1");
            urls.add("/rbac/2");
            urls.add("/rbac/3");

            // 匹配 url
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())){
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
