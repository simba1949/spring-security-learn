package vip.openpark.security.authentication.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author anthony
 * @since 2024/1/25 19:45
 */
public class JwtUtils {
	// 盐值
	private static final String SALT = "LyeXro0VaE^!p";
	
	/**
	 * 创建 token
	 *
	 * @param map 负载 map
	 * @return String
	 */
	public static String getToken(Map<String, String> map) {
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DATE, 5);
		
		JWTCreator.Builder builder = JWT.create();
		
		map.forEach(builder::withClaim);
		
		return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SALT));
	}
	
	/**
	 * 验证token
	 *
	 * @param token token
	 * @return 验证token合法性，不合法就会抛出异常
	 */
	public static DecodedJWT verify(String token) {
		return JWT.require(Algorithm.HMAC256(SALT)).build().verify(token);
	}
}