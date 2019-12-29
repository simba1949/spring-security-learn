package top.simba1949.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.simba1949.common.rbac.Permission;

import java.util.List;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 9:09
 */
public interface PermissionMapper extends Mapper<Permission> {
    List<Permission> getPermissionByUsername(@Param("username") String username);
}
