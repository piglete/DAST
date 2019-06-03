package club.emergency.by_b_system_notice.model;

import club.map.core.model.RootObject;
import club.map.core.model.TableName;

@TableName("by_b_system_notice")
public class ByBSystemNotice extends RootObject {
    private String noticeTitle;//通知标题

    private String noticeMessage;//通知信息

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeMessage() {
        return noticeMessage;
    }

    public void setNoticeMessage(String noticeMessage) {
        this.noticeMessage = noticeMessage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", noticeTitle=").append(noticeTitle);
        sb.append(", noticeMessage=").append(noticeMessage);
        sb.append("]");
        return sb.toString();
    }
}