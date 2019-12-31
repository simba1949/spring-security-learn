package top.simba1949.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/30 22:03
 */
public class ValidateException extends AuthenticationException {

    public ValidateException(String msg) {
        super(msg);
    }
}
