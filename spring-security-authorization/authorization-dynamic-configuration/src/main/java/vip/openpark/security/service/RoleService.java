package vip.openpark.security.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.openpark.security.domain.RoleDO;
import vip.openpark.security.mapper.RoleDOMapper;

import java.util.Collections;
import java.util.List;

/**
 * @author anthony
 * @version 2024/2/5 16:42
 */
@Slf4j
@Service
public class RoleService {
	@Resource
	private RoleDOMapper roleDOMapper;
	
	/**
	 * 根据用户id查询角色
	 *
	 * @param userId 用户id
	 * @return 角色code
	 */
	public List<String> selectByUserId(Long userId) {
		if (null == userId) {
			return Collections.emptyList();
		}
		
		List<RoleDO> roleDOList = roleDOMapper.selectByUserId(userId);
		return roleDOList.stream().map(RoleDO::getCode).toList();
	}
}