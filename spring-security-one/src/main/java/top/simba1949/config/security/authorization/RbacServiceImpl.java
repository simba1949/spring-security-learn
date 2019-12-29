package top.simba1949.config.security.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.simba1949.common.rbac.Permission;
import top.simba1949.service.PermissionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 17:06
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    @Autowired
    private PermissionService permissionService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // 判断结果
        boolean hasPermission = false;

        String requestURI = request.getRequestURI();
        // 如果是静态页面的话可以直接放行
        if (requestURI.endsWith(".html")){
            return true;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails){
            // 获取用户名
            String username = ((UserDetails) principal).getUsername();
            // 获取用户名下所拥有权限的资源
            List<Permission> permissions = permissionService.getPermissionByUsername(username);
            // 判断用户该请求用户是否有权限访问
            for (Permission permission : permissions) {
                String method = request.getMethod();
                boolean methodFlag = method.equalsIgnoreCase(permission.getReqMethod());
                boolean uriFlag = antPathMatcher.match(permission.getUri(), requestURI);
                if (methodFlag && uriFlag){
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
