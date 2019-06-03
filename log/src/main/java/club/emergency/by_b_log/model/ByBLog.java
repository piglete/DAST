package club.emergency.by_b_log.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_log")
public class ByBLog extends RootObject {
    private String loginName;//用户账号

    private String moduleName;//模块名称

    private String operationName;//操作名称

    private String ip;//访问ip

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", loginName=").append(loginName);
        sb.append(", moduleName=").append(moduleName);
        sb.append(", operationName=").append(operationName);
        sb.append(", ip=").append(ip);
        sb.append("]");
        return sb.toString();
    }
}