package vip.openpark.security.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vip.openpark.security.mapper.PermissionDOMapper;

import java.util.Collections;
import java.util.List;

/**
 * @author anthony
 * @since 2024/2/5 21:01
 */
@Service
public class PermissionService {
	@Resource
	private PermissionDOMapper permissionDOMapper;
	
	public List<String> selectByRoles(List<String> roles) {
		if (CollectionUtils.isEmpty(roles)){
			return Collections.emptyList();
		}
		
		return permissionDOMapper.selectByRoles(roles);
	}
}