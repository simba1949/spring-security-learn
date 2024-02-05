package vip.openpark.security.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import vip.openpark.security.domain.PermissionDO;

import java.util.List;

public interface PermissionDOMapper extends Mapper<PermissionDO> {
	List<String> selectByRoles(@Param("roles") List<String> roles);
}