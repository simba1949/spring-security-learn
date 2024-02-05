package vip.openpark.security.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * 基于方法授权的配置
 *
 * @author anthony
 * @since 2024/2/5 22:11
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSpelConfig {
}