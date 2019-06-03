package club.emergency.project.manager.impl;

import club.emergency.project.manager.ByRRecordRevertManager;
import club.emergency.project.mapper.ByBStoreRecordMapper;
import club.emergency.project.mapper.ByRRecordRevertMapper;
import club.emergency.project.model.ByRRecordRevert;
import club.map.base.util.ExcelData;
import club.map.base.util.ExportExcelUtils;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import com.wanqing.util.StringHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ByRRecordRevertManagerImpl extends GenericManagerImpl<ByRRecordRevert, Integer> implements ByRRecordRevertManager {

    private ByBStoreRecordMapper byBStoreRecordMapper;

    @Autowired(required = false)
    public ByRRecordRevertManagerImpl(ByRRecordRevertMapper mapper,
                                      ByBStoreRecordMapper byBStoreRecordMapper) {
        super(mapper, ByRRecordRevert.class);
        this.byBStoreRecordMapper = byBStoreRecordMapper;
    }

    /**
     * 分页查询
     *
     * @param recordTypeName 项目类型
     * @param revertPerson   归还人
     * @param revertState    归还状态
     * @param pageNo         页码
     * @param pageSize       每页显示数量
     * @return
     */
    @Override
    public Page search(String recordTypeName, String revertPerson, String revertState, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByRRecordRevert.class);
        flipFilter.addSearch(recordTypeName, Operate.EQUAL, "recordTypeName");
        flipFilter.addSearch(revertPerson, Operate.EQUAL, "revertPerson");
        flipFilter.addSearch(revertState, Operate.EQUAL, "revertState");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        return this.flipUsingInPage(flipFilter);
    }

    /**
     * 1.归还记录新增
     * 2.修改在档案库的存在状态
     *
     * @param byRRecordRevert
     */
    @Transactional
    @Override
    public void upperSave(ByRRecordRevert byRRecordRevert) {
        //添加归还记录
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operateDate = simpleDateFormat.format(date);
        byRRecordRevert.setOperateDate(operateDate);
        this.save(byRRecordRevert);
        //修改档案在档案库的存在状态
        Integer id = byRRecordRevert.getStoreRecordId();
        String recordState = "存在";
        byBStoreRecordMapper.updateRecordStateById(recordState, id);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

    /**
     * 导出excel
     *
     * @param fileName
     * @param response
     */
    @Override
    public void export(String fileName, HttpServletResponse response) {
        ExcelData excelData = new ExcelData();
        String name = fileName;
        //设置excel名称
        if (StringHandler.isNotEmptyOrNull(fileName)) {
            excelData.setName(fileName);
        } else {
            Long date = System.currentTimeMillis();
            excelData.setName(date.toString());
            name = date.toString();
        }
        //设置excel标题
        List<String> titles = new ArrayList<>();
        titles.add("档案号");
        titles.add("档案类型");
        titles.add("部门");
        titles.add("归还人");
        titles.add("归还日期");
        titles.add("归还类型");
        titles.add("管理员");
        titles.add("操作时间");
        titles.add("备注");
        excelData.setTitles(titles);
        //填充数据
        FlipFilter flipFilter = new FlipFilter(ByRRecordRevert.class);
        List<ByRRecordRevert> byRRecordRevertList = this.list(flipFilter);
        List<List<Object>> rows = new ArrayList<>();
        for (ByRRecordRevert byRRecordRevert : byRRecordRevertList) {
            List<Object> row = new ArrayList<>();
            row.add(byRRecordRevert.getFileNumber());
            row.add(byRRecordRevert.getRecordTypeName());
            row.add(byRRecordRevert.getDepartmentName());
            row.add(byRRecordRevert.getRevertPerson());
            row.add(byRRecordRevert.getRevertDate());
            row.add(byRRecordRevert.getRevertState());
            row.add(byRRecordRevert.getManager());
            row.add(byRRecordRevert.getOperateDate());
            row.add(byRRecordRevert.getRemark());
            rows.add(row);
        }
        excelData.setRows(rows);
        try {
            ExportExcelUtils.exportExcel(response, name, excelData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}