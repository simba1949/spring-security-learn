package top.simba1949.controller;

import cn.hutool.core.util.RandomUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/30 22:50
 */
@Slf4j
@RestController
@RequestMapping("account")
public class AccountController {

    public static final String SESSION_KEY_ACCOUNT = "AccountKey";

    @GetMapping("send-code")
    public void sendCode(HttpServletRequest request) throws ServletRequestBindingException {
        String account = ServletRequestUtils.getStringParameter(request, "account");
        // 判断 account 是手机号码还是邮件账号
        Preconditions.checkArgument(null != account, "the account doesn't blank");

        // 生成随机验证码
        String code = RandomUtil.randomNumbers(4);
        if (account.contains("@")){
            // 发送邮箱验证码
            log.info("已经发送邮箱验证码为：{}", code);
        }else {
            // 发送手机验证码
            log.info("已经发送手机验证码为：{}", code);
        }

        // 存储验证码（推荐使用redis等中间件存储，示例使用session存储）
        request.getSession().setAttribute(account, code);
        log.info("account code is {}", request.getSession().getAttribute(account).toString());
    }
}
