package club.emergency.project.manager.impl;

import club.emergency.project.manager.ByPlusTipMessageManager;
import club.emergency.project.mapper.ByPlusTipMessageMapper;
import club.emergency.project.model.ByPlusTipMessage;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ByPlusTipMessageManagerImpl extends GenericManagerImpl<ByPlusTipMessage, Integer> implements ByPlusTipMessageManager {

    private ByPlusTipMessageMapper byPlusTipMessageMapper;

    @Autowired
    public ByPlusTipMessageManagerImpl(ByPlusTipMessageMapper mapper,
                                       ByPlusTipMessageMapper byPlusTipMessageMapper) {
        super(mapper, ByPlusTipMessage.class);
        this.byPlusTipMessageMapper = byPlusTipMessageMapper;
    }

    @Transactional
    @Override
    public void upperSave(ByPlusTipMessage byPlusTipMessage) {
        this.save(byPlusTipMessage);
    }

    /**
     * 删除提示消息
     *
     * @param projectId
     */
    @Transactional
    @Override
    public void deleteMessageByProject(Integer projectId) {
        byPlusTipMessageMapper.removeMessage(projectId);
    }

    /**
     * 提示消息查询,查询的时候统计当前时间距离预期结束时间的时间
     *
     * @param projectId
     * @return
     */
    @Override
    public List<ByPlusTipMessage> search(Integer projectId) {
        //获取提示消息
        FlipFilter flipFilter = new FlipFilter(ByPlusTipMessage.class);
        flipFilter.addSearch(projectId, Operate.EQUAL, "projectId");
        List<ByPlusTipMessage> byPlusTipMessageList = this.list(flipFilter);
        for (ByPlusTipMessage byPlusTipMessage : byPlusTipMessageList) {
            this.searchTipMessageInfo(byPlusTipMessage);
        }
        return byPlusTipMessageList;
    }

    /**
     * 获取提示消息详情
     *
     * @param byPlusTipMessage
     */
    private void searchTipMessageInfo(ByPlusTipMessage byPlusTipMessage) {
        //获取项目名称
        Integer projectId = byPlusTipMessage.getProjectId();
        String projectName = byPlusTipMessageMapper.searchProjectName(projectId);
        byPlusTipMessage.setProjectName(projectName);
        //提示消息内容
        String tipDate = byPlusTipMessage.getTipDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tip = byPlusTipMessage.getMessageContent();
        try {
            //fDate为项目预期结束日期,nDate为当前日期
            Date fDate = sdf.parse(tipDate);
            String nowDate = sdf.format(new Date());
            Date nDate = sdf.parse(nowDate);
            //相差天数
            long days = (fDate.getTime() - nDate.getTime()) / (1000 * 3600 * 24);
            int diffDay = new Long(days).intValue();
            int absDiffDay = Math.abs(diffDay);
            if (diffDay > 0) {
                tip = "距" + projectName + "项目预期结束时间还有" + diffDay + "天!";
            } else if (diffDay == 0) {
                tip = "今天是" + projectName + "项目预期结束日期的最后一天!";
            } else {
                tip = projectName + "项目,已超期" + absDiffDay + "天!";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        byPlusTipMessage.setMessageContent(tip);
    }
}