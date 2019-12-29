package top.simba1949.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 14:14
 */
public final class UserUtils {
    /**
     * 获取当前用户信息
     * @return
     */
    public static Authentication getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
