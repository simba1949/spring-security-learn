package top.simba1949.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.simba1949.common.response.CommonResponse;
import top.simba1949.common.response.ResponseBuilder;

/**
 * 全局异常处理器
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/29 14:40
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResponse globalExceptionSolve(Exception e){
        log.error("this exception is what {}", e.getMessage(), e);
        return ResponseBuilder.buildFailure(e.getMessage());
    }
}
