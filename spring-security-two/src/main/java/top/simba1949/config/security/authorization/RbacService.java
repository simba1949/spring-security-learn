package top.simba1949.config.security.authorization;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验接口
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/31 10:06
 */
public interface RbacService {
    /**
     * 权限控制
     * @param request
     * @param authentication
     * @return
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
