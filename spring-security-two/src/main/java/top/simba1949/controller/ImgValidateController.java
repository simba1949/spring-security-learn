package top.simba1949.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码生成接口
 * @AUTHOR Theodore
 * @DATE 2019/12/30 15:58
 */
@Slf4j
@RestController
@RequestMapping("img/validate")
public class ImgValidateController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @GetMapping("create")
    public void createImgValidate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 20);
        String code = lineCaptcha.getCode();
        log.info(code);
        request.getSession().setAttribute(SESSION_KEY, code);
        lineCaptcha.write(response.getOutputStream());
        response.flushBuffer();
    }
}
