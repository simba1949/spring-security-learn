package top.simba1949.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限测试类
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/31 10:09
 */
@RestController
@RequestMapping("rbac")
public class RbacController {

    @GetMapping("0")
    public Integer rbac_0(){
        return 0;
    }

    @GetMapping("1")
    public Integer rbac_1(){
        return 1;
    }

    @GetMapping("2")
    public Integer rbac_2(){
        return 2;
    }

    @GetMapping("3")
    public Integer rbac_3(){
        return 3;
    }

    @GetMapping("4")
    public Integer rbac_4(){
        return 4;
    }
}
