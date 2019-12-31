package top.simba1949.config.security.account;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/30 22:42
 */
public class AccountAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * 认证
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccountAuthenticationToken authenticationToken = (AccountAuthenticationToken) authentication;

        // 读取用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationToken.getPrincipal().toString());
        if (null == userDetails){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 第一个参数传递用户信息userDetails， 第二个参数传递用户的权限
        AccountAuthenticationToken authenticationResult = new AccountAuthenticationToken(userDetails.getUsername(), userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断传入的是否是 AccountAuthenticationToken 类
        return AccountAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
