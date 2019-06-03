package club.emergency.by_b_region.manager.impl;

import club.emergency.by_b_region.manager.ByBRegionManager;
import club.emergency.by_b_region.mapper.ByBRegionMapper;
import club.emergency.by_b_region.model.ByBRegion;
import club.emergency.by_b_region.model.RegionTreeUtil;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

@Service
public class ByBRegionManagerImpl extends GenericManagerImpl<ByBRegion, Integer> implements ByBRegionManager {

    private ByBRegionMapper byBRegionMapper;

    @Autowired
    public ByBRegionManagerImpl(ByBRegionMapper mapper,
                                ByBRegionMapper byBRegionMapper) {
        super(mapper, ByBRegion.class);
        this.byBRegionMapper = byBRegionMapper;
    }

    /**
     * 查询树形结构区域,带排序
     *
     * @return
     */
    @Override
    public List<ByBRegion> treeSearch() {
        FlipFilter flipFilter = new FlipFilter(ByBRegion.class);
        //设置排序字段,建议选择int类型字段
        flipFilter.setSortField("sortNum");
        List bySRegionList = this.list(flipFilter);
        return RegionTreeUtil.getTreeData(bySRegionList);
    }

    /**
     * 区域编辑(新增和编辑)
     * 新增时,需要根据当前上一节点的id,即当前节点的parentId,设置当前节点的code
     * 修改时,不允许修改code和排序sortNum
     *
     * @param byBRegion
     */
    @Transactional
    @Override
    public void upperSave(ByBRegion byBRegion) {
        String code = byBRegion.getCode();
        //判断当前操作是新增还是修改,新增id为null,修改id不为空
        if (byBRegion.getId() == null) {
            if (byBRegion.getParentId() != null) {
                //获取当前节点最大的code,如果当前code为null,则说明该节点是新节点
                String maxCode = byBRegionMapper.queryMaxCodeByParentId(code + "%", byBRegion.getParentId());
                if (maxCode != null) {
                    BigInteger bigInteger = new BigInteger(maxCode);
                    BigInteger result = bigInteger.add(BigInteger.valueOf(1));
                    byBRegion.setCode(result + "");
                } else {
                    byBRegion.setCode(code + "100");
                }
                Integer maxSortNum = byBRegionMapper.queryMaxSortNumByParentId(code + "%", byBRegion.getParentId());
                if (maxSortNum != null) {
                    maxSortNum++;
                    byBRegion.setSortNum(maxSortNum);
                } else {
                    byBRegion.setSortNum(1);
                }
            } else {
                String maxCodeForRoot = byBRegionMapper.queryMaxCodeForRoot();
                byBRegion.setCode(maxCodeForRoot);
            }
        }
        if (code.length() == 6 || code.length() == 3) {
            byBRegion.setFullName(byBRegion.getName());
        } else {
            String name = byBRegion.getName();
            Integer parentId = byBRegion.getParentId();
            String parentFullName = this.get(parentId).getFullName();
            String fullName = parentFullName + name;
            byBRegion.setFullName(fullName);
        }
        this.save(byBRegion);
    }

    /**
     * 批量删除,删除该节点时同时删除该节点下的所有子节点
     * 一般不允许删除,被其他模块依赖
     *
     * @param ids
     */
    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            ByBRegion byBRegion = this.get(Integer.valueOf(id));
            String code = byBRegion.getCode();
            FlipFilter flipFilter = new FlipFilter(ByBRegion.class);
            flipFilter.addSearch(code + "%", Operate.LIKE, "code");
            List<ByBRegion> byBRegionList = this.list(flipFilter);
            for (int i = 0; i < byBRegionList.size(); i++) {
                this.remove(byBRegionList.get(i).getId());
            }
        });
    }


    /**
     * 获取所有最后一级节点
     * code编排规则为后一级比前一级多'100',例如第一级为 '100',则第二级为 '100100',其他类似
     *
     * @return
     */
    @Override
    public List<ByBRegion> search() {
        List<Integer> list = new ArrayList<>();
        FlipFilter flipFilter = new FlipFilter(ByBRegion.class);
        List<ByBRegion> byBRegionList = this.list(flipFilter);
        for (ByBRegion byBRegion : byBRegionList) {
            String code = byBRegion.getCode();
            if (code.length() == 9) {
                list.add(byBRegion.getId());
            }
        }
        FlipFilter flipFilter1 = new FlipFilter(ByBRegion.class);
        flipFilter1.addSearch(list, Operate.IN, "parentId");
        List<ByBRegion> byBRegionList1 = this.list(flipFilter1);
        return byBRegionList1;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Override
    public ByBRegion searchDetails(Integer id) {
        ByBRegion byBRegion = this.get(id);
        Integer parentId = byBRegion.getParentId();
        if (parentId != null) {
            String parentName = this.get(parentId).getName();
            byBRegion.setParentName(parentName);
        } else {
            byBRegion.setParentName(byBRegion.getName());
        }
        return byBRegion;
    }


    @Override
    public ByBRegion searchByCode(String code) {
        ByBRegion byBRegion = byBRegionMapper.searchByCode(code);
        return byBRegion;
    }

    @Override
    public List<ByBRegion> treeList() {
        FlipFilter flipFilter = new FlipFilter(ByBRegion.class);
        return this.list(flipFilter);
    }

    /**
     * 根据父节点查询所有子节点
     *
     * @param parentId
     * @return
     */
    @Override
    public Map<String, String> searchRegion(Integer parentId) {
        FlipFilter flipFilter = new FlipFilter(ByBRegion.class);
        flipFilter.addSearch(parentId, Operate.EQUAL, "parentId");
        flipFilter.setSortField("sortNum");
        List<ByBRegion> byBRegionList = this.list(flipFilter);
        Map<String, String> map = new HashMap<String, String>();
        for (ByBRegion byBRegion : byBRegionList) {
            map.put(byBRegion.getCode(), byBRegion.getName());
        }
        return map;
    }

    /**
     * 查询该code下的所有子节点,不包含自己
     *
     * @param code
     * @return
     */
    @Override
    public List<ByBRegion> searchChildByCode(String code) {
        FlipFilter flipFilter = new FlipFilter(ByBRegion.class);
        flipFilter.addSearch(code + "%", Operate.LIKE, "code");
        List<ByBRegion> byBRegionList = this.list(flipFilter);
        byBRegionList.removeIf(e -> code.equals(e.getCode()));
        return byBRegionList;
    }
}