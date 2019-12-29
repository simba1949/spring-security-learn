package top.simba1949.config.security;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义同一用户在不同终端登录的处理
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/29 16:02
 */
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("并发登录");
    }
}
