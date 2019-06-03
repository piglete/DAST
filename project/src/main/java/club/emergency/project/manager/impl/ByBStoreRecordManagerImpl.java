package club.emergency.project.manager.impl;

import club.emergency.by_b_storeroom.manager.ByBStoreroomManager;
import club.emergency.project.manager.ByBStoreRecordManager;
import club.emergency.project.mapper.ByBStoreRecordMapper;
import club.emergency.project.mapper.ByRRecordMapper;
import club.emergency.project.model.ByBStoreRecord;
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
public class ByBStoreRecordManagerImpl extends GenericManagerImpl<ByBStoreRecord, Integer> implements ByBStoreRecordManager {

    private ByBStoreroomManager byBStoreroomManager;
    private ByRRecordMapper byRRecordMapper;

    @Autowired
    public ByBStoreRecordManagerImpl(ByBStoreRecordMapper mapper,
                                     ByBStoreroomManager byBStoreroomManager,
                                     ByRRecordMapper byRRecordMapper
    ) {
        super(mapper, ByBStoreRecord.class);
        this.byBStoreroomManager = byBStoreroomManager;
        this.byRRecordMapper = byRRecordMapper;
    }

    /**
     * 档案入库信息编辑(新增和修改)
     *
     * @param byBStoreRecord
     */
    @Transactional
    @Override
    public void upperSave(ByBStoreRecord byBStoreRecord) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addDate = simpleDateFormat.format(date);
        byBStoreRecord.setOperateDate(addDate);
        this.save(byBStoreRecord);
        byRRecordMapper.updateIsStoreroomById(byBStoreRecord.getRecordId());
    }

    @Override
    public ByBStoreRecord searchDetails(Integer id) {
        ByBStoreRecord byBStoreRecord = this.get(id);
        this.searchRecordInfo(byBStoreRecord);
        return byBStoreRecord;
    }

    /**
     * 入库信息批量删除
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
     * 多条件模糊分页查询
     *
     * @param storeroomCode
     * @param fileNumber
     * @param recordTypeName
     * @param departmentName
     * @param recordState
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page search(String storeroomCode, String fileNumber, String recordTypeName, String departmentName, String recordState, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBStoreRecord.class);
        flipFilter.addSearch(storeroomCode + "%", Operate.LIKE, "storeroomCode");
        flipFilter.addSearch("%" + fileNumber + "%", Operate.LIKE, "fileNumber");
        flipFilter.addSearch(recordTypeName, Operate.EQUAL, "recordTypeName");
        flipFilter.addSearch(departmentName, Operate.EQUAL, "departmentName");
        flipFilter.addSearch(recordState, Operate.EQUAL, "recordState");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByBStoreRecord> byBStoreRecordList = page.getListInfo();
        for (ByBStoreRecord byBStoreRecord : byBStoreRecordList) {
            //获取档案相关信息
            byBStoreRecord = this.searchRecordInfo(byBStoreRecord);
        }
        return page;
    }

    /**
     * 导出excel
     * 已设置好一个excel处理工具类 ExportExcelUtils
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
        //设置excel标题 标题必须和对应的列信息一一对应
        List<String> titles = new ArrayList<>();
        titles.add("档案号");
        titles.add("档案类型");
        titles.add("所属部门");
        titles.add("档案位置");
        titles.add("管理员");
        titles.add("入库日期");
        titles.add("备注");
        titles.add("档案状态");
        excelData.setTitles(titles);
        //填充数据
        FlipFilter flipFilter = new FlipFilter(ByBStoreRecord.class);
        List<ByBStoreRecord> byBStoreRecordList = this.list(flipFilter);
        List<List<Object>> rows = new ArrayList();
        for (ByBStoreRecord byBStoreRecord : byBStoreRecordList) {
            byBStoreRecord = this.searchRecordInfo(byBStoreRecord);
            List<Object> row = new ArrayList();
            row.add(byBStoreRecord.getFileNumber());
            row.add(byBStoreRecord.getRecordTypeName());
            row.add(byBStoreRecord.getDepartmentName());
            row.add(byBStoreRecord.getStoreroomFullName());
            row.add(byBStoreRecord.getOperator());
            row.add(byBStoreRecord.getOperateDate());
            row.add(byBStoreRecord.getRemark());
            row.add(byBStoreRecord.getRecordState());
            rows.add(row);
        }
        excelData.setRows(rows);
        try {
            ExportExcelUtils.exportExcel(response, name, excelData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询入库信息详情
     *
     * @param byBStoreRecord
     * @return
     */
    private ByBStoreRecord searchRecordInfo(ByBStoreRecord byBStoreRecord) {
        Integer recordId = byBStoreRecord.getRecordId();
        //获取库房名称
        String storeroomFullName = byBStoreroomManager.searchByCode(byBStoreRecord.getStoreroomCode()).getFullName();
        byBStoreRecord.setStoreroomFullName(storeroomFullName);
        return byBStoreRecord;
    }
}