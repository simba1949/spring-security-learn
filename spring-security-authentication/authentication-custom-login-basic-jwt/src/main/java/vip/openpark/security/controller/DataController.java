package vip.openpark.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anthony
 * @since 2024/2/6 0:13
 */
@RestController
@RequestMapping
public class DataController {
	
	@GetMapping("data")
	public String getData() {
		return "SUCCESS";
	}
}