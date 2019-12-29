package top.simba1949.service;

import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Pageable;
import top.simba1949.common.rbac.Role;

import java.util.List;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:03
 */
public interface RoleService {

    void insert(Role role);

    void update(Role role);

    void delete(Integer id);

    Role get(Integer id);

    PageInfo<Role> list(Role role, Pageable pageable);

    List<Role> selectUserByUser(Integer userId);
}
