package club.emergency.by_b_storeroom.manager.impl;

import club.emergency.by_b_storeroom.manager.ByBStoreroomManager;
import club.emergency.by_b_storeroom.mapper.ByBStoreroomMapper;
import club.emergency.by_b_storeroom.model.ByBStoreroom;
import club.emergency.by_b_storeroom.model.StoreTreeUtil;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Service
public class ByBStoreroomManagerImpl extends GenericManagerImpl<ByBStoreroom, Integer> implements ByBStoreroomManager {

    private ByBStoreroomMapper byBStoreroomMapper;

    @Autowired
    public ByBStoreroomManagerImpl(ByBStoreroomMapper mapper,
                                   ByBStoreroomMapper byBStoreroomMapper) {
        super(mapper, ByBStoreroom.class);
        this.byBStoreroomMapper = byBStoreroomMapper;
    }

    /**
     * 库房新增时,需要计算code值和排序处理
     *
     * @param byBStoreroom
     */
    @Transactional
    @Override
    public void upperSave(ByBStoreroom byBStoreroom) {
        String code = byBStoreroom.getCode();
        if (byBStoreroom.getId() == null) {
            if (byBStoreroom.getParentId() != null) {
                //判断该节点是否是当前同级节点的第一个节点,maxCode为null,说明是第一个节点,不为null,则说明不是
                String maxCode = byBStoreroomMapper.queryMaxCodeByParentId(byBStoreroom.getParentId());
                if (maxCode != null) {
                    //进行+1数学运算,获取新code
                    BigInteger bigInteger = new BigInteger(maxCode);
                    BigInteger result = bigInteger.add(BigInteger.valueOf(1));
                    byBStoreroom.setCode(result + "");
                } else {
                    byBStoreroom.setCode(code + "100");
                }
                //判断该节点是否是当前同级节点的第一个节点,maxSortNum为null,说明是第一个节点,不为null,则说明不是
                Integer maxSortNum = byBStoreroomMapper.queryMaxSortNumByParentId(byBStoreroom.getParentId());
                if (maxSortNum != null) {
                    maxSortNum++;
                    byBStoreroom.setSortNum(maxSortNum);
                } else {
                    byBStoreroom.setSortNum(1);
                }
            } else {
                //父节点为null时,则说明该新增数据是定级节点
                byBStoreroom.setCode("100");
                byBStoreroom.setSortNum(1);
            }
        }
        //库房全称处理
        if (code.length() == 3 || "".equals(code)) {
            byBStoreroom.setFullName(byBStoreroom.getName());
        } else {
            String name = byBStoreroom.getName();
            Integer parentId = byBStoreroom.getParentId();
            String parentFullName = this.get(parentId).getFullName();
            String fullName = parentFullName + name;
            byBStoreroom.setFullName(fullName);
        }
        this.save(byBStoreroom);
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Override
    public ByBStoreroom searchDetails(Integer id) {
        ByBStoreroom byBStoreroom = this.get(id);
        Integer parentId = byBStoreroom.getParentId();
        if (parentId != null) {
            String parentName = this.get(parentId).getName();
            byBStoreroom.setParentName(parentName);
        } else {
            byBStoreroom.setParentName(byBStoreroom.getName());
        }
        return byBStoreroom;
    }

    /**
     * 批量删除,删除该节点的同时删除该节点下的所有子节点
     *
     * @param ids
     */
    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            ByBStoreroom byBStoreroom = this.get(Integer.valueOf(id));
            String code = byBStoreroom.getCode();
            FlipFilter flipFilter = new FlipFilter(ByBStoreroom.class);
            flipFilter.addSearch(code + "%", Operate.LIKE, "code");
            List<ByBStoreroom> byBStoreroomList = this.list(flipFilter);
            for (int i = 0; i < byBStoreroomList.size(); i++) {
                this.remove(byBStoreroomList.get(i).getId());
            }
        });
    }

    /**
     * 根据code查询库房信息,code值是唯一的
     *
     * @param code
     * @return
     */
    @Override
    public ByBStoreroom searchByCode(String code) {
        ByBStoreroom byBStoreroom = this.get("code", code);
        return byBStoreroom;
    }

    @Override
    public List<ByBStoreroom> search() {
        FlipFilter flipFilter = new FlipFilter(ByBStoreroom.class);
        flipFilter.setSortField("sortNum");
        return this.list(flipFilter);
    }

    /**
     * StoreTreeUtil树工具类
     *
     * @return
     */
    @Override
    public List<ByBStoreroom> treeSearch() {
        FlipFilter flipFilter = new FlipFilter(ByBStoreroom.class);
        flipFilter.setSortField("sortNum");
        List list = this.list(flipFilter);
        return StoreTreeUtil.getTreeData(list);
    }
}