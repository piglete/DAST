package club.emergency.project.manager.impl;

import club.emergency.project.manager.ByPlusOutCheckMessageManager;
import club.emergency.project.mapper.ByPlusOutCheckMessageMapper;
import club.emergency.project.model.ByPlusOutCheckMessage;
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
public class ByPlusOutCheckMessageManagerImpl extends GenericManagerImpl<ByPlusOutCheckMessage, Integer> implements ByPlusOutCheckMessageManager {

    private ByPlusOutCheckMessageMapper byPlusOutCheckMessageMapper;

    @Autowired
    public ByPlusOutCheckMessageManagerImpl(ByPlusOutCheckMessageMapper mapper,
                                            ByPlusOutCheckMessageMapper byPlusOutCheckMessageMapper) {
        super(mapper, ByPlusOutCheckMessage.class);
        this.byPlusOutCheckMessageMapper = byPlusOutCheckMessageMapper;
    }

    /**
     * 查询提示消息,填写修改内容
     *
     * @param taskId
     * @return
     */
    @Override
    public List<ByPlusOutCheckMessage> search(Integer taskId) {
        //获取提示消息
        FlipFilter flipFilter = new FlipFilter(ByPlusOutCheckMessage.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        List<ByPlusOutCheckMessage> byPlusOutCheckMessageList = this.list(flipFilter);
        for (ByPlusOutCheckMessage byPlusOutCheckMessage : byPlusOutCheckMessageList) {
            this.searchOutCheckMessageInfo(byPlusOutCheckMessage);
        }
        return byPlusOutCheckMessageList;
    }

    /**
     * 查询详情,查询时动态改变提示内容
     *
     * @param byPlusOutCheckMessage
     */
    private void searchOutCheckMessageInfo(ByPlusOutCheckMessage byPlusOutCheckMessage) {
        //获取任务名称
        Integer taskId = byPlusOutCheckMessage.getTaskId();
        String taskName = byPlusOutCheckMessageMapper.searchTaskName(taskId);
        byPlusOutCheckMessage.setTaskName(taskName);
        //获取二检人员
        Integer twoCheckUserId = byPlusOutCheckMessage.getTwoCheckUserId();
        String twoCheckUser = byPlusOutCheckMessageMapper.searchUsername(twoCheckUserId);
        byPlusOutCheckMessage.setTwoCheckUser(twoCheckUser);
        //提示消息内容
        String tipDate = byPlusOutCheckMessage.getOutCheckFinishDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tip = byPlusOutCheckMessage.getMessageContent();
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
                tip = "距" + taskName + "任务预期结束时间还有" + diffDay + "天!";
            } else if (diffDay == 0) {
                tip = "今天是" + taskName + "任务预期结束日期的最后一天!";
            } else {
                tip = taskName + "任务,已超期" + absDiffDay + "天!";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        byPlusOutCheckMessage.setMessageContent(tip);
    }

    @Transactional
    @Override
    public void upperSave(ByPlusOutCheckMessage byPlusOutCheckMessage) {
        this.save(byPlusOutCheckMessage);
    }

    @Transactional
    @Override
    public void removeByTaskId(Integer taskId) {
        byPlusOutCheckMessageMapper.removeByTaskId(taskId);
    }
}