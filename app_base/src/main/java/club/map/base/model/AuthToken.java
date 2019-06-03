package club.map.base.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("auth_token")
public class AuthToken extends RootObject {
    private String token;

    private Integer type;

    private Integer foreignId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getForeignId() {
        return foreignId;
    }

    public void setForeignId(Integer foreignId) {
        this.foreignId = foreignId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", token=").append(token);
        sb.append(", type=").append(type);
        sb.append(", foreignId=").append(foreignId);
        sb.append("]");
        return sb.toString();
    }
}