package vip.openpark.security.mapper;

import tk.mybatis.mapper.common.Mapper;
import vip.openpark.security.domain.RoleDO;

import java.util.List;

public interface RoleDOMapper extends Mapper<RoleDO> {
	List<RoleDO> selectByUserId(Long userId);
}