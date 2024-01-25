package vip.openpark.security.authentication.common;

import lombok.*;

import java.io.Serializable;

/**
 * @author anthony
 * @version 2023/11/28 19:57
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 响应状态：true 表示请求成功，false 表示请求失败
	 */
	private Boolean status;
	/**
	 * 响应码
	 */
	private String code;
	/**
	 * 响应信息，例如错误提示
	 */
	private String message;
	/**
	 * 响应数据
	 */
	private T data;
}