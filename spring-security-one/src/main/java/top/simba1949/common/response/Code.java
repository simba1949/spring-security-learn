package top.simba1949.common.response;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 10:09
 */
public enum  Code {
    SUCCESS("0000", "成功"),
    ERROR_SYSTEM("0001", "Internal System Error"),
    ERROR_COMMON_NOT_FIND("0002", "The data cannot be updated because it cannot be queried")

    ;

    private String code;
    private String msg;

    Code(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
