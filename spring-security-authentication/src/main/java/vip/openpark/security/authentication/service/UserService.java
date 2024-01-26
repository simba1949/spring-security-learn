package vip.openpark.security.authentication.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.openpark.security.authentication.domain.UserDO;
import vip.openpark.security.authentication.mapper.UserDOMapper;

import java.util.List;

/**
 * @author anthony
 * @since 2024/1/25 20:56
 */
@Slf4j
@Service
public class UserService {
	@Resource
	private UserDOMapper userDOMapper;
	
	public Long registerCount() {
		return userDOMapper.count();
	}
	
	/**
	 * 判断用户是否存在
	 *
	 * @param username 用户名
	 * @return true 表示未注册，false 表示已注册
	 */
	public boolean isRegister(String username) {
		UserDO userDO = get(username);
		return null == userDO;
	}
	
	public List<UserDO> all() {
		return userDOMapper.selectAll();
	}
	
	public UserDO get(String username) {
		UserDO userDO = new UserDO();
		userDO.setUsername(username);
		return userDOMapper.selectOne(userDO);
	}
}