package club.map.core.result;

/**
 * @Description: 返回的JSON数据结构标准
 * @Author: lxl
 * @CreateDate: 2019/05/22
 * @Version: v1.0
 */
public class ResponseBean <T> {
    private boolean success;
    private T data;
    private String code;
    private String message;



    public ResponseBean(){}

    public ResponseBean(boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "success=" + success +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public ResponseBean(boolean success, T data, String code, String message) {
        super();
        this.success = success;
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public ResponseBean(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
    public ResponseBean(boolean success,ResultEnums enums){
        this.success=success;
        this.code=enums.getCode();
        this.code=enums.getMsg();
    }
    public ResponseBean(boolean success,T data,ResultEnums enums){
        this.success=success;
        this.data=data;
        this.code=enums.getCode();
        this.message=enums.getMsg();
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
