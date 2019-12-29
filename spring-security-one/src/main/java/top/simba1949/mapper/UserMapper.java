package top.simba1949.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.simba1949.common.rbac.User;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:04
 */
public interface UserMapper extends Mapper<User> {

    User selectByUsername(@Param("username") String username);
}
