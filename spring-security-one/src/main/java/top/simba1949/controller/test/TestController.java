package top.simba1949.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/26 17:42
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("hello")
    public String sayHello(){
        return "Hello World";
    }
}
