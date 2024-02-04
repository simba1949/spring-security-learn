package vip.openpark.security.common.response;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author anthony
 * @version 2023/11/28 19:57
 */
@Getter
@ToString
public class Response<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String SUCCESS_CODE = "000000";
	public static final String SUCCESS_MESSAGE = "操作成功";
	public static final String ERROR_CODE = "E100000";
	public static final String ERROR_MESSAGE = "系统异常";
	
	/**
	 * 响应状态：true 表示请求成功，false 表示请求失败
	 */
	private boolean status;
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
	
	private Response() {
	}
	
	private Response(boolean status, String code, String message, T data) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	/**
	 * 响应成功
	 *
	 * @return 响应
	 */
	public static <T> Response<T> success() {
		return success(null);
	}
	
	/**
	 * 响应成功
	 *
	 * @param data 响应数据
	 * @param <T>  泛型
	 * @return Response
	 */
	public static <T> Response<T> success(T data) {
		return success(SUCCESS_CODE, SUCCESS_MESSAGE, data);
	}
	
	/**
	 * 响应成功
	 *
	 * @param code    错误码 code
	 * @param message 错误码 message
	 * @param data    响应数据
	 * @param <T>     泛型
	 * @return Response
	 */
	public static <T> Response<T> success(String code, String message, T data) {
		return new Response<>(Boolean.TRUE, code, message, data);
	}
	
	/**
	 * 响应失败（系统异常）
	 *
	 * @param <T> 泛型
	 * @return Response
	 */
	public static <T> Response<T> systemFail() {
		return fail(ERROR_CODE, ERROR_MESSAGE);
	}
	
	/**
	 * 响应失败
	 *
	 * @param message 错误码 message
	 * @return Response
	 */
	public static <T> Response<T> fail(String message) {
		return fail(ERROR_CODE, message);
	}
	
	/**
	 * 响应失败
	 *
	 * @param code    错误码 code
	 * @param message 错误码 message
	 * @return Response
	 */
	public static <T> Response<T> fail(String code, String message) {
		return fail(code, message, null);
	}
	
	/**
	 * 响应失败
	 *
	 * @param code    错误码
	 * @param message 错误码 message
	 * @param data    响应数据
	 * @param <T>     泛型
	 * @return Response
	 */
	public static <T> Response<T> fail(String code, String message, T data) {
		return new Response<>(Boolean.FALSE, code, message, data);
	}
	
	// =============================================
	// =========== getter/toString ==========
	// =============================================
}