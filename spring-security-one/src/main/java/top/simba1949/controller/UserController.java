package top.simba1949.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import top.simba1949.common.rbac.User;
import top.simba1949.common.response.CommonResponse;
import top.simba1949.common.response.ResponseBuilder;
import top.simba1949.service.UserService;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:01
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public CommonResponse insert(@RequestBody User user){
        userService.insert(user);
        return ResponseBuilder.buildSuccess();
    }

    @DeleteMapping("{id:[\\d]+}")
    public CommonResponse delete(@PathVariable("id") Integer id){
        userService.delete(id);
        return ResponseBuilder.buildSuccess();
    }

    @PutMapping
    public CommonResponse update(@RequestBody User user){
        userService.update(user);
        return ResponseBuilder.buildSuccess();
    }

    @GetMapping("{id:[\\d]+}")
    public CommonResponse get(@PathVariable("id") Integer id){
        User user = userService.get(id);
        return ResponseBuilder.buildSuccess(user);
    }

    @GetMapping("list")
    public CommonResponse list(User user, Pageable pageable){
        PageInfo<User> list = userService.list(user, pageable);
        return ResponseBuilder.buildSuccess(list);
    }
}
