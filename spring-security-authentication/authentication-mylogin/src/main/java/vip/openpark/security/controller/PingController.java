package vip.openpark.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anthony
 * @version 2024/1/25 13:46
 */
@RestController
public class PingController {
	@GetMapping("ping")
	public String ping() {
		return "pong";
	}
}