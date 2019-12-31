package top.simba1949.config.security.account;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/30 22:28
 */
public class AccountAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // ~ Static fields/initializers
    // =====================================================================================
    /**
     * account 可以是 email 也可以是 mobile，请求中接收到账号的name
     */
    public static final String SPRING_SECURITY_FORM_ACCOUNT_KEY = "account";

    private String accountParameter = SPRING_SECURITY_FORM_ACCOUNT_KEY;
    private boolean postOnly = true;

    // ~ Constructors
    // ===================================================================================================

    public AccountAuthenticationFilter() {
        // 匹配哪些路径
        super(new AntPathRequestMatcher("/user/account", "POST"));
    }

    // ~ Methods
    // ========================================================================================================

    /**
     * 认证流程
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String account = obtainAccount(request);

        if (account == null) {
            account = "";
        }
        account = account.trim();

        AccountAuthenticationToken authRequest = new AccountAuthenticationToken(account);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取账号
     * @param request
     * @return
     */
    protected String obtainAccount(HttpServletRequest request) {
        return request.getParameter(accountParameter);
    }

    /**
     *
     * @param request
     * @param authRequest
     */
    protected void setDetails(HttpServletRequest request, AccountAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setAccountParameter(String accountParameter) {
        Assert.hasText(accountParameter, "Account parameter must not be empty or null");
        this.accountParameter = accountParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getAccountParameter() {
        return accountParameter;
    }
}
