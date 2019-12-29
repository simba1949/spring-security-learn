package top.simba1949.config.security.authorization;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义用户权限不通过处理器
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/29 17:57
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 返回json形式的错误信息
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println("无权限（应该将CommonResponse返回给前端）");
        response.getWriter().flush();
    }
}
