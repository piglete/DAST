package club.emergency.Util;

/**
 * Created by zero-mac on 16/6/28.
 */
public class ResultJson {


    /**
     * 返回码
     */
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 返回码
     */

    private int count;
    /**
     * 返回结果描述
     */
    private String msg;

    /**
     * 返回内容
     */
    private Object data;


}
