package top.simba1949.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 10:07
 */
@Data
public class CommonResponse implements Serializable {
    private static final long serialVersionUID = -2939943217910095745L;

    private boolean status;
    private Code code;
    private String msg;
    private Object data;

    public CommonResponse(boolean status, Code code, String msg, Object data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
