package top.simba1949.mapper;

import tk.mybatis.mapper.common.Mapper;
import top.simba1949.common.rbac.Role;

import java.util.List;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:07
 */
public interface RoleMapper extends Mapper<Role> {
    List<Role> selectUserByUser(Integer userId);
}
