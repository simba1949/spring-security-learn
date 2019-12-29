package top.simba1949.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import top.simba1949.common.rbac.Role;
import top.simba1949.common.response.CommonResponse;
import top.simba1949.common.response.ResponseBuilder;
import top.simba1949.service.RoleService;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:01
 */
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public CommonResponse insert(@RequestBody Role role){
        Preconditions.checkArgument(null != role && null != role.getRoleName(), "role & roleName is not empty");
        if (!role.getRoleName().startsWith(Role.ROLE_PREFIX)){
            role.setRoleName(Role.ROLE_PREFIX + role.getRoleName());
        }
        roleService.insert(role);
        return ResponseBuilder.buildSuccess();
    }

    @DeleteMapping("{id:[\\d]+}")
    public CommonResponse delete(@PathVariable("id") Integer id){
        roleService.delete(id);
        return ResponseBuilder.buildSuccess();
    }

    @PutMapping
    public CommonResponse update(@RequestBody Role role){
        roleService.update(role);
        return ResponseBuilder.buildSuccess();
    }

    @GetMapping("{id:[\\d]+}")
    public CommonResponse get(@PathVariable("id") Integer id){
        Role role = roleService.get(id);
        return ResponseBuilder.buildSuccess(role);
    }

    @GetMapping("list")
    public CommonResponse list(Role role, Pageable pageable){
        PageInfo<Role> list = roleService.list(role, pageable);
        return ResponseBuilder.buildSuccess(list);
    }
}
