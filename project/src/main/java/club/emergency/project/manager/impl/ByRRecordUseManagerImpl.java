package club.emergency.project.manager.impl;

import club.emergency.project.manager.ByRRecordUseManager;
import club.emergency.project.mapper.ByBStoreRecordMapper;
import club.emergency.project.mapper.ByRRecordUseMapper;
import club.emergency.project.model.ByRRecordUse;
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
public class ByRRecordUseManagerImpl extends GenericManagerImpl<ByRRecordUse, Integer> implements ByRRecordUseManager {

    private ByBStoreRecordMapper byBStoreRecordMapper;

    @Autowired
    public ByRRecordUseManagerImpl(ByRRecordUseMapper mapper,
                                   ByBStoreRecordMapper byBStoreRecordMapper) {
        super(mapper, ByRRecordUse.class);
        this.byBStoreRecordMapper = byBStoreRecordMapper;
    }

    /**
     * 分页查询
     *
     * @param recordTypeName
     * @param purpose
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page search(String recordTypeName, String purpose, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByRRecordUse.class);
        flipFilter.addSearch(recordTypeName, Operate.EQUAL, "recordTypeName");
        flipFilter.addSearch("%" + purpose + "%", Operate.LIKE, "purpose");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        return this.flipUsingInPage(flipFilter);
    }

    /**
     * 新增借出记录,同时修改档案存在状态,建议硬性编码数据通过配置表配置
     *
     * @param byRRecordUse
     */
    @Transactional
    @Override
    public void upperSave(ByRRecordUse byRRecordUse) {
        //添加档案借出记录
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addDate = simpleDateFormat.format(date);
        byRRecordUse.setAddDate(addDate);
        this.save(byRRecordUse);
        //修改档案在档案库的存在状态
        Integer id = byRRecordUse.getStoreRecordId();
        String recordState = "借出";
        byBStoreRecordMapper.updateRecordStateById(recordState, id);
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }

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
        titles.add("借用人");
        titles.add("借用用途");
        titles.add("借阅日期");
        titles.add("管理员");
        titles.add("应还日期");
        titles.add("操作时间");
        titles.add("备注");
        excelData.setTitles(titles);
        //填充数据
        FlipFilter flipFilter = new FlipFilter(ByRRecordUse.class);
        List<ByRRecordUse> byRRecordUseList = this.list(flipFilter);
        List<List<Object>> rows = new ArrayList();
        for (ByRRecordUse byRRecordUse : byRRecordUseList) {
            List<Object> row = new ArrayList();
            row.add(byRRecordUse.getFileNumber());
            row.add(byRRecordUse.getRecordTypeName());
            row.add(byRRecordUse.getDepartmentName());
            row.add(byRRecordUse.getBorrower());
            row.add(byRRecordUse.getPurpose());
            row.add(byRRecordUse.getBorrowDate());
            row.add(byRRecordUse.getManager());
            row.add(byRRecordUse.getExpectedReturnDate());
            row.add(byRRecordUse.getAddDate());
            row.add(byRRecordUse.getRemark());
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