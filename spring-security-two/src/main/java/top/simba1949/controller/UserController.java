package top.simba1949.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取当前用户信息
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/30 15:02
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("me0")
    public Authentication authentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("me1")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @GetMapping("me2")
    public UserDetails authentication(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }
}
