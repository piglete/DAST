package club.emergency.project.manager.impl;

import club.emergency.project.manager.ByPlusTaskQualityManager;
import club.emergency.project.mapper.ByPlusTaskMapper;
import club.emergency.project.mapper.ByPlusTaskQualityMapper;
import club.emergency.project.model.ByPlusTaskQuality;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ByPlusTaskQualityManagerImpl extends GenericManagerImpl<ByPlusTaskQuality, Integer> implements ByPlusTaskQualityManager {

    private ByPlusTaskMapper byPlusTaskMapper;

    @Autowired
    public ByPlusTaskQualityManagerImpl(ByPlusTaskQualityMapper mapper,
                                        ByPlusTaskMapper byPlusTaskMapper) {
        super(mapper, ByPlusTaskQuality.class);
        this.byPlusTaskMapper = byPlusTaskMapper;
    }

    @Override
    public List<ByPlusTaskQuality> search(Integer taskId, Integer checkDepartment) {
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskQuality.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        flipFilter.addSearch(checkDepartment, Operate.EQUAL, "checkDepartment");
        return this.list(flipFilter);
    }

    /**
     * 详情查询
     *
     * @param taskId
     * @param checkDepartment
     * @return
     */
    @Override
    public ByPlusTaskQuality searchInfo(Integer taskId, Integer checkDepartment) {
        ByPlusTaskQuality byPlusTaskQuality = null;
        FlipFilter flipFilter = new FlipFilter(ByPlusTaskQuality.class);
        flipFilter.addSearch(taskId, Operate.EQUAL, "taskId");
        flipFilter.addSearch(checkDepartment, Operate.EQUAL, "checkDepartment");
        List<ByPlusTaskQuality> byPlusTaskQualityList = this.list(flipFilter);
        if (byPlusTaskQualityList.size() == 1) {
            byPlusTaskQuality = byPlusTaskQualityList.get(0);
        }
        return byPlusTaskQuality;
    }

    /**
     * 计算分数,由于二检和三检分别占权重不同,所以得分需要分别处理
     *
     * @param byPlusTaskQuality
     */
    @Transactional
    @Override
    public String upperSave(ByPlusTaskQuality byPlusTaskQuality) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addTime = sdf.format(new Date());
        Integer taskId = byPlusTaskQuality.getTaskId();
        Integer checkDepartment = byPlusTaskQuality.getCheckDepartment();
        //判断是否有A类错误,如果存在,分数为0;如果不存在,则为计算所得分数
        Double taskScore = this.getTaskScore(byPlusTaskQuality);
        if (checkDepartment == 2) {
            byPlusTaskMapper.updateTwoScore(taskScore, taskId);
        } else if (checkDepartment == 3) {
            byPlusTaskMapper.updateThreeScore(taskScore, taskId);
        }
        byPlusTaskQuality.setAssessDate(addTime);
        byPlusTaskQuality.setScore(taskScore);
        this.save(byPlusTaskQuality);
        return taskScore.toString();
    }

    /**
     * 计算该类型分数,计算分数时,如果某个类型有A类错误,分数直接为0
     *
     * @param byPlusTaskQuality
     * @return
     */
    private Double getTaskScore(ByPlusTaskQuality byPlusTaskQuality) {
        Double taskScore = byPlusTaskQuality.getScore();
        //获取数据及结构正确性中A类、B类、C类、D类的错误数
        Integer dataOneError = byPlusTaskQuality.getDataOneError();
        Integer dataTwoError = byPlusTaskQuality.getDataTwoError();
        Integer dataThreeError = byPlusTaskQuality.getDataThreeError();
        Integer dataFourError = byPlusTaskQuality.getDataFourError();
        //获取地理精度中A类、B类、C类、D类的错误数
        Integer geographicOneError = byPlusTaskQuality.getGeographicOneError();
        Integer geographicTwoError = byPlusTaskQuality.getGeographicTwoError();
        Integer geographicThreeError = byPlusTaskQuality.getGeographicThreeError();
        Integer geographicFourError = byPlusTaskQuality.getGeographicFourError();
        //获取整饰质量中A类、B类、C类、D类的错误数
        Integer groomOneError = byPlusTaskQuality.getGroomOneError();
        Integer groomTwoError = byPlusTaskQuality.getGroomTwoError();
        Integer groomThreeError = byPlusTaskQuality.getGroomThreeError();
        Integer groomFourError = byPlusTaskQuality.getGroomFourError();
        //获取附件质量中A类、B类、C类、D类的错误数
        Integer attachmentOneError = byPlusTaskQuality.getAttachmentOneError();
        Integer attachmentTwoError = byPlusTaskQuality.getAttachmentTwoError();
        Integer attachmentThreeError = byPlusTaskQuality.getAttachmentThreeError();
        Integer attachmentFourError = byPlusTaskQuality.getAttachmentFourError();
        //判断是否有A类错误,如果没有A类错误才可计算得分,否则直接分数为0,如果有A类错误,对应的个数不为0,
        //所以判断如果均不为0,则说明都没有A类错误,然后根据各自的权重,计算综合得分
        if (dataOneError == 0 && geographicOneError == 0 && groomOneError == 0 && attachmentOneError == 0) {
            //数据及结构的权重为25%,计算得分
            Double dsWeight = 0.25;
            Double dsScore = this.getScore(dataOneError, dataTwoError, dataThreeError, dataFourError, dsWeight);
            //地理精度的权重为35%,计算得分
            Double gaWeight = 0.35;
            Double gaScore = this.getScore(geographicOneError, geographicTwoError, geographicThreeError, geographicFourError, gaWeight);
            //整饰质量的权重为25%,计算得分
            Double fqWeight = 0.25;
            Double fqScore = this.getScore(groomOneError, groomTwoError, groomThreeError, groomFourError, fqWeight);
            //附件质量的权重为15%,计算得分
            Double aqWeight = 0.15;
            Double aqScore = this.getScore(attachmentOneError, attachmentTwoError, attachmentThreeError, attachmentFourError, aqWeight);
            BigDecimal b1 = new BigDecimal(Double.toString(dsScore));
            BigDecimal b2 = new BigDecimal(Double.toString(gaScore));
            BigDecimal b3 = new BigDecimal(Double.toString(fqScore));
            BigDecimal b4 = new BigDecimal(Double.toString(aqScore));
            taskScore = Double.valueOf((b1.add(b2).add(b3).add(b4)).toString());
        } else {
            taskScore = 0.0;
        }
        return taskScore;
    }

    /**
     * 计算某种类型的得分(计算某个类型得分时,该类型得分不能为负数)
     *
     * @param oneError   A类错误一个扣40分
     * @param twoError   B类错误一个扣12分
     * @param threeError C类错误一个扣4分
     * @param fourError  D类错误一个扣1分
     * @param weight
     * @return
     */
    private Double getScore(Integer oneError, Integer twoError, Integer threeError, Integer fourError, Double weight) {
        Double score = 0.0;
        Double realScore = weight * (100 - oneError * 40 - twoError * 12 - threeError * 4 - fourError * 1);
        if (realScore > 0) {
            score = realScore;
        }
        return score;
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    /**
     * 二检或者三检接收任务时,系统初始给定的一个默认评分
     *
     * @param id
     * @param checkDepartment
     * @param username
     * @param addTime
     */
    @Override
    public void createRecord(Integer id, Integer checkDepartment, String username, String addTime) {
        ByPlusTaskQuality byPlusTaskQuality = new ByPlusTaskQuality();
        byPlusTaskQuality.setTaskId(id);
        byPlusTaskQuality.setDataOneError(0);
        byPlusTaskQuality.setDataTwoError(0);
        byPlusTaskQuality.setDataThreeError(0);
        byPlusTaskQuality.setDataFourError(0);
        byPlusTaskQuality.setGeographicOneError(0);
        byPlusTaskQuality.setGeographicTwoError(0);
        byPlusTaskQuality.setGeographicThreeError(0);
        byPlusTaskQuality.setGeographicFourError(0);
        byPlusTaskQuality.setGroomOneError(0);
        byPlusTaskQuality.setGroomTwoError(0);
        byPlusTaskQuality.setGroomThreeError(0);
        byPlusTaskQuality.setGroomFourError(0);
        byPlusTaskQuality.setAttachmentOneError(0);
        byPlusTaskQuality.setAttachmentTwoError(0);
        byPlusTaskQuality.setAttachmentThreeError(0);
        byPlusTaskQuality.setAttachmentFourError(0);
        byPlusTaskQuality.setAssessUser(username);
        byPlusTaskQuality.setAssessDate(addTime);
        byPlusTaskQuality.setCheckDepartment(checkDepartment);
        byPlusTaskQuality.setScore(100.0);
        this.save(byPlusTaskQuality);
    }
}