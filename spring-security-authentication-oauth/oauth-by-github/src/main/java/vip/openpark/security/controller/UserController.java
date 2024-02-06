package vip.openpark.security.controller;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anthony
 * @version 2024/2/6 17:16
 */
@Slf4j
@RestController
public class UserController {
	
	@GetMapping("me")
	public String currentUser(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
	                          @AuthenticationPrincipal OAuth2User oauth2User) {
		log.info("currentUser: {}", JSON.toJSONString(authorizedClient));
		log.info("currentUser: {}", JSON.toJSONString(oauth2User));
		return "currentUser: " + oauth2User.getAttributes().get("name");
	}
}