package top.simba1949.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 7:55
 */
@Component
public final class EncryptUtils {

    private static PasswordEncoder encoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        encoder = passwordEncoder;
    }

    /**
     * 加密
     * @param encryptStr
     * @return
     */
    public static String encoder( String encryptStr){
        return encoder.encode(encryptStr);
    }
}
