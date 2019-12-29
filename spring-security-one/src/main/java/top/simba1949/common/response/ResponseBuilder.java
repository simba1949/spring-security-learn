package top.simba1949.common.response;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 10:14
 */
public class ResponseBuilder {
    /**
     * 成功，无返回值
     * @return
     */
    public static CommonResponse buildSuccess(){
        return new CommonResponse(true, Code.SUCCESS, null, null);
    }

    /**
     * 成功，有返回值
     * @param data
     * @return
     */
    public static CommonResponse buildSuccess(Object data){
        return new CommonResponse(true, Code.SUCCESS, null, data);
    }

    /**
     * 失败，无返回值，无错误码
     * @param msg
     * @return
     */
    public static CommonResponse buildFailure(String msg){
        return new CommonResponse(false, null, msg, null);
    }

    /**
     * 失败，无返回值
     * @param code
     * @param msg
     * @return
     */
    public static CommonResponse buildFailure(Code code, String msg){
        return new CommonResponse(false, code, msg, null);
    }

    /**
     * 失败，有返回值
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static CommonResponse buildFailure(Code code, String msg, Object data){
        return new CommonResponse(false, code, msg, data);
    }

    /**
     * 系统级错误
     * @return
     */
    public static CommonResponse buildSystemFailure(){
        return new CommonResponse(false, Code.ERROR_SYSTEM, null, null);
    }
}
