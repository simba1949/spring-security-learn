package top.simba1949.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import top.simba1949.common.BaseCommon;
import top.simba1949.common.rbac.Permission;
import top.simba1949.common.response.CommonResponse;
import top.simba1949.common.response.ResponseBuilder;
import top.simba1949.service.PermissionService;
import top.simba1949.util.UserUtils;

import java.time.LocalDateTime;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:02
 */
@Slf4j
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public CommonResponse insert(@RequestBody Permission permission){
        permissionService.insert(permission);
        return ResponseBuilder.buildSuccess();
    }

    @DeleteMapping("{id:[\\d]+}")
    public CommonResponse delete(@PathVariable("id") Integer id){
        permissionService.delete(id);
        return ResponseBuilder.buildSuccess();
    }

    @PutMapping
    public CommonResponse update(@RequestBody Permission permission){
        permissionService.update(permission);
        return ResponseBuilder.buildSuccess();
    }

    @GetMapping("{id:[\\d]+}")
    public CommonResponse get(@PathVariable("id") Integer id){
        Permission permission = permissionService.get(id);
        return ResponseBuilder.buildSuccess(permission);
    }

    @GetMapping("list")
    public CommonResponse list(Permission permission, Pageable pageable){
        PageInfo<Permission> list = permissionService.list(permission, pageable);
        return ResponseBuilder.buildSuccess(list);
    }
}
