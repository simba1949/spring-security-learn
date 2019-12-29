package top.simba1949.service;

import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Pageable;
import top.simba1949.common.rbac.Permission;

import java.util.List;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:03
 */
public interface PermissionService {

    void insert(Permission permission);

    void update(Permission permission);

    void delete(Integer id);

    Permission get(Integer id);

    PageInfo<Permission> list(Permission permission, Pageable pageable);

    List<Permission> getPermissionByUsername(String username);
}
