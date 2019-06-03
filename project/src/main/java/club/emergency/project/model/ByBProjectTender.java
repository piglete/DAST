package club.emergency.project.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_project_tender")
public class ByBProjectTender extends RootObject {
    private String projectName;//项目名称

    private String agentCompany;//代理公司

    private String bondTime;//保证金时间

    private Float bond;//保证金

    private String openTime;//开标时间

    private String openAddress;//开标地址

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAgentCompany() {
        return agentCompany;
    }

    public void setAgentCompany(String agentCompany) {
        this.agentCompany = agentCompany;
    }

    public String getBondTime() {
        return bondTime;
    }

    public void setBondTime(String bondTime) {
        this.bondTime = bondTime;
    }

    public Float getBond() {
        return bond;
    }

    public void setBond(Float bond) {
        this.bond = bond;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOpenAddress() {
        return openAddress;
    }

    public void setOpenAddress(String openAddress) {
        this.openAddress = openAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectName=").append(projectName);
        sb.append(", agentCompany=").append(agentCompany);
        sb.append(", bondTime=").append(bondTime);
        sb.append(", bond=").append(bond);
        sb.append(", openTime=").append(openTime);
        sb.append(", openAddress=").append(openAddress);
        sb.append("]");
        return sb.toString();
    }
}