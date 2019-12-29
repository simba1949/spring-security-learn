package top.simba1949.config.security.authorization;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 17:04
 */
public interface RbacService {
    /**
     * 对某些资源是否有权限
     * @param request
     * @param authentication
     * @return
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
