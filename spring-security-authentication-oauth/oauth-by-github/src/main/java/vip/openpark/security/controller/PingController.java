package vip.openpark.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anthony
 * @since 2024/1/24 22:17
 */
@RestController
public class PingController {
	@GetMapping("ping")
	public String ping() {
		return "pong";
	}
}