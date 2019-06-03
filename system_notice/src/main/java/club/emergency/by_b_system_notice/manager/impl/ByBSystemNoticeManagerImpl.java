package club.emergency.by_b_system_notice.manager.impl;

import club.emergency.by_b_system_notice.manager.ByBSystemNoticeManager;
import club.emergency.by_b_system_notice.mapper.ByBSystemNoticeMapper;
import club.emergency.by_b_system_notice.model.ByBSystemNotice;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class ByBSystemNoticeManagerImpl extends GenericManagerImpl<ByBSystemNotice, Integer> implements ByBSystemNoticeManager {

    @Autowired
    public ByBSystemNoticeManagerImpl(ByBSystemNoticeMapper mapper) {
        super(mapper, ByBSystemNotice.class);
    }

    /**
     * 多字段模糊查询匹配
     *
     * @param content
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page search(String content, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBSystemNotice.class);
        flipFilter.addOrSearch("%" + content + "%", Operate.LIKE, "noticeTitle", "noticeMessage");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        return this.flipUsingInPage(flipFilter);
    }

    @Transactional
    @Override
    public void upperSave(ByBSystemNotice byBSystemNotice) {
        this.save(byBSystemNotice);
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            this.remove(Integer.valueOf(id));
        });
    }
}