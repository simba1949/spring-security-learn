package vip.openpark.security.common.config;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vip.openpark.security.common.util.JwtUtils;

import java.io.IOException;

/**
 * @author anthony
 * @since 2024/2/5 23:53
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
	@Resource
	private RedissonClient redissonClient;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 获取 JWT
		String jwt = request.getHeader("Authorization");
		if (StrUtil.isNotBlank(jwt)) {
			// 验证解析 JWT
			JwtUtils.verify(jwt);
			// 从 redis 中获取用户信息
			RBucket<Authentication> bucket = redissonClient.getBucket(jwt);
			// 判断是否存在
			if (bucket.isExists()) {
				// 存放到 SecurityContextHolder 中
				// TODO ANTHONY 这里反序列化会有一个问题
				Authentication authentication = bucket.get();
				authentication.setAuthenticated(true); // 设置为已认证
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		// 直接放行
		filterChain.doFilter(request, response);
	}
}