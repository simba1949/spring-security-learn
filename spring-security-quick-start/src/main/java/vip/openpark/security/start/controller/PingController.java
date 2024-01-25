package vip.openpark.security.start.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anthony
 * @since 2024/1/24 22:17
 */
@Slf4j
@RestController
public class PingController {
	
	@GetMapping("ping")
	public String ping() {
		return "pong";
	}
}