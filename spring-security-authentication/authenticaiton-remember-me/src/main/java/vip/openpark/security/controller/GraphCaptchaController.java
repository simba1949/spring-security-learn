package vip.openpark.security.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.openpark.security.common.constant.SpringSecurityConstant;

import java.io.IOException;

/**
 * @author anthony
 * @since 2024/2/3 14:53
 */
@Slf4j
@RestController
@RequestMapping("graphCaptcha")
public class GraphCaptchaController {
	
	@RequestMapping("generate")
	public void generate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 20);
		String code = lineCaptcha.getCode();
		log.info(code);
		
		request.getSession().setAttribute(SpringSecurityConstant.SESSION_KEY, code);
		lineCaptcha.write(response.getOutputStream());
		response.flushBuffer();
	}
}