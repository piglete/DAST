package club.emergency.by_b_return_reason.manager.impl;

import club.emergency.by_b_return_reason.manager.ByBReturnReasonManager;
import club.emergency.by_b_return_reason.mapper.ByBReturnReasonMapper;
import club.emergency.by_b_return_reason.model.ByBReturnReason;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ByBReturnReasonManagerImpl extends GenericManagerImpl<ByBReturnReason, Integer> implements ByBReturnReasonManager {

    private ByBReturnReasonMapper byBReturnReasonMapper;

    @Autowired
    public ByBReturnReasonManagerImpl(ByBReturnReasonMapper mapper,
                                      ByBReturnReasonMapper byBReturnReasonMapper
    ) {
        super(mapper, ByBReturnReason.class);
        this.byBReturnReasonMapper = byBReturnReasonMapper;
    }

    /**
     * 获取退回原因列表
     *  1.判断前台是否传入parentId值,如果没有,则默认为查询所有顶级内容
     *  2.将回退原因分类处理
     * @return
     */
    @Override
    public List<ByBReturnReason> searchWithRelation() {
        FlipFilter flipFilter = new FlipFilter(ByBReturnReason.class);
        flipFilter.addSearch(34, Operate.EQUAL, "parentId");
        List<ByBReturnReason> byBReturnReasonList = this.list(flipFilter);
        for (ByBReturnReason byBReturnReason : byBReturnReasonList) {
            Integer id=byBReturnReason.getId();
            FlipFilter flipFilter1=new FlipFilter(ByBReturnReason.class);
            flipFilter1.addSearch(id,Operate.EQUAL,"parentId");
            List<ByBReturnReason> byBReturnReasonList1=this.list(flipFilter1);
            byBReturnReason.setChildReason(byBReturnReasonList1);
        }
        return byBReturnReasonList;
    }

    @Transactional
    @Override
    public void upperSave(ByBReturnReason byBReturnReason) {
        this.save(byBReturnReason);
    }

    @Override
    public ByBReturnReason searchDetails(Integer id) {
        ByBReturnReason byBReturnReason = this.get(id);
        Integer parentId=byBReturnReason.getParentId();
        if(parentId != null){
            ByBReturnReason byBReturnReason1=this.get(parentId);
            byBReturnReason.setParentName(byBReturnReason1.getContent());
        }else{
            byBReturnReason.setParentName(byBReturnReason.getContent());
        }
        return byBReturnReason;
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            Integer pid = Integer.valueOf(id);
            //判断该节点是不是父节点,如果是父节点,则先删除父节点下的所有子节点,再删除父节点
            ByBReturnReason byBReturnReason = this.get(pid);
            Integer parentId = byBReturnReason.getParentId();
            if (parentId != null) {
                byBReturnReasonMapper.removeByParentId(pid);
            }
            this.remove(pid);
        });
    }

    @Override
    public List<ByBReturnReason> search(Integer parentId) {
        FlipFilter flipFilter=new FlipFilter(ByBReturnReason.class);
        flipFilter.addSearch(parentId,Operate.EQUAL,"parentId");
        return this.list(flipFilter);
    }
}