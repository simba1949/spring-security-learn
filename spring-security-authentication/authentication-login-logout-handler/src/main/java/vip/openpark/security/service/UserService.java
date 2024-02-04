package vip.openpark.security.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.openpark.security.domain.UserDO;
import vip.openpark.security.mapper.UserDOMapper;

/**
 * @author anthony
 * @since 2024/1/25 20:56
 */
@Slf4j
@Service
public class UserService {
	@Resource
	private UserDOMapper userDOMapper;
	
	public UserDO get(String username) {
		UserDO userDO = new UserDO();
		userDO.setUsername(username);
		return userDOMapper.selectOne(userDO);
	}
}