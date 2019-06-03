package club.map.base.model;

/**
 * Created by zero-mac on 17/3/10.
 */
public enum AuthType {

    ADMIN(1),//1.管理员,2.教师,3.学生
    LEADER(2),//
    STAFF(3),//
    ;

    int code;

    AuthType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
