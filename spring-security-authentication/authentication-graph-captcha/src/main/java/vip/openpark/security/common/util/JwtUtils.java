package vip.openpark.security.common.util;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import vip.openpark.security.domain.UserDO;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anthony
 * @since 2024/1/25 19:45
 */
public class JwtUtils {
	// 盐值
	private static final String SECRET = "SECRET";
	
	/**
	 * 创建 token
	 * <a href="https://jwt.io/">解析 JWT</a>
	 * java-jwt 网址：
	 * <a href="https://github.com/auth0/java-jwt">java-jwt</a>
	 * <a href="https://github.com/auth0/java-jwt/blob/master/EXAMPLES.md#jwt-creation">创建 token</a>
	 *
	 * @param userDO 用户信息
	 * @return String
	 */
	public static String createToken(UserDO userDO) {
		// JWT header
		// JWT header 是用来描述JWT元数据的JSON对象，包括两部分
		// 1. alg - 签名使用的算法
		// 2. typ - 表示令牌的类型，在JWT令牌统一写为JWT
		Map<String, Object> header = new HashMap<>();
		header.put("alg", "");
		header.put("typ", "JWT");
		// JWT payload 是JWT的主体内容部分,也是一个JSON对象,包含需要传递的数据,在JWT中默认有一下七个字段供选择
		// 这七个预定义字段并不要求强制使用,并且除以上默认字段外,我们还可以自定义私有字段,例如将包含用户信息的数据放到 payload 中
		// 1. iss - 发行人
		// 2. sub - 主题
		// 3. aud - 用户
		// 4. iat - JWT的签发时间
		// 5. exp - JWT的过期时间
		// 6. jti - JWT的唯一标识
		// 7. nbf - 在此之前不可用
		// 【注意】：Claim values must only be of types Map, List, Boolean, Integer, Long, Double, String, Date, Instant, and Null
		Map<String, Object> payload = new HashMap<>();
		payload.put("iss", "露天时代");
		payload.put("sub", "JWT");
		payload.put("aud", userDO.getId());
		payload.put("iat", Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8))));
		payload.put("exp", Date.from(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.ofHours(8))));
		payload.put("jti", userDO.getId());
		payload.put("userInfo", JSONObject.toJSONString(userDO));
		// JWT Signature 签名，用来校验数据是否被篡改的
		// JWT中签名算法有三种：
		// 1. HMAC【哈希消息验证码(对称)】：HS256/HS384/HS512
		// 2. RSASSA【RSA签名算法(非对称)】（RS256/RS384/RS512）
		// 3. ECDSA【椭圆曲线数据签名算法(非对称)】（ES256/ES384/ES512）
		Algorithm algorithm = Algorithm.HMAC384(SECRET);
		
		return JWT.create()
			.withHeader(header)
			.withPayload(payload)
			.sign(algorithm);
	}
	
	/**
	 * 验证 JWT
	 * <a href="https://github.com/auth0/java-jwt">java-jwt</a>
	 * <a href="https://github.com/auth0/java-jwt/blob/master/EXAMPLES.md#jwt-verification">解析 JWT</a>
	 *
	 * @param jwt jwt
	 * @return 验证 jwt 合法性，不合法就会抛出异常
	 */
	public static DecodedJWT verify(String jwt) {
		// 使用的算法和secret要与创建token时保持一致
		Algorithm algorithm = Algorithm.HMAC384(SECRET);
		// 创建解析对象
		JWTVerifier verifier = JWT.require(algorithm).build();
		return verifier.verify(jwt);
	}
}