package club.emergency.by_b_department.manager.impl;

import club.emergency.by_b_department.manager.ByBDepartmentManager;
import club.emergency.by_b_department.mapper.ByBDepartmentMapper;
import club.emergency.by_b_department.model.ByBDepartment;
import club.map.base.util.AppTreeUtil;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ByBDepartmentManagerImpl extends GenericManagerImpl<ByBDepartment, Integer> implements ByBDepartmentManager {

    private ByBDepartmentMapper byBDepartmentMapper;

    @Autowired
    public ByBDepartmentManagerImpl(ByBDepartmentMapper mapper,
                                    ByBDepartmentMapper byBDepartmentMapper
    ) {
        super(mapper, ByBDepartment.class);
        this.byBDepartmentMapper = byBDepartmentMapper;
    }

    @Transactional
    @Override
    public void upperSave(ByBDepartment byBDepartment) {
        String code = byBDepartment.getCode();
        if (byBDepartment.getId() == null) {
            if (byBDepartment.getParentId() != null) {
                String maxCode = byBDepartmentMapper.queryMaxCodeByParentId(code + "%", byBDepartment.getParentId());
                if (maxCode != null) {
                    BigInteger bigInteger = new BigInteger(maxCode);
                    BigInteger result = bigInteger.add(BigInteger.valueOf(1));
                    byBDepartment.setCode(result + "");
                } else {
                    byBDepartment.setCode(code + "100");
                }
                Integer maxSortNum = byBDepartmentMapper.queryMaxSortNumByParentId(code + "%", byBDepartment.getParentId());
                if (maxSortNum != null) {
                    maxSortNum++;
                    byBDepartment.setSortNum(maxSortNum);
                } else {
                    byBDepartment.setSortNum(1);
                }
            } else {
                byBDepartment.setCode("100");
            }
        }
        this.save(byBDepartment);
    }

    /**
     * 删除部门时,删除该节点及其子节点
     *
     * @param ids
     */
    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            ByBDepartment byBDepartment = this.get(Integer.valueOf(id));
            String code = byBDepartment.getCode();
            FlipFilter flipFilter = new FlipFilter(ByBDepartment.class);
            flipFilter.addSearch(code + "%", Operate.LIKE, "code");
            List<ByBDepartment> byBDepartmentList = this.list(flipFilter);
            for (ByBDepartment bDepartment : byBDepartmentList) {
                Integer pid = byBDepartment.getId();
                this.remove(pid);
            }
        });
    }

    @Override
    public List<ByBDepartment> treeSearch() {
        FlipFilter flipFilter = new FlipFilter(ByBDepartment.class);
        List list = this.list(flipFilter);
        return AppTreeUtil.getTreeData(list);
    }

    @Override
    public List<ByBDepartment> search() {
        FlipFilter flipFilter = new FlipFilter(ByBDepartment.class);
        List<ByBDepartment> byBDepartmentList = this.list(flipFilter);
        for (ByBDepartment byBDepartment : byBDepartmentList) {
            //部门是否可下达
            Integer releaseFlag = byBDepartment.getReleaseFlag();
            if (releaseFlag == 0) {
                byBDepartment.setRelease("不可下达");
            } else if (releaseFlag == 1) {
                byBDepartment.setRelease("可下达");
            }
            //部门作废
            Integer invalidFlag = byBDepartment.getInvalidFlag();
            if (invalidFlag == 0) {
                byBDepartment.setInvalid("使用中");
            } else if (invalidFlag == 1) {
                byBDepartment.setInvalid("已作废");
            }
        }
        return byBDepartmentList;
    }

    /**
     * 获取所有的子部门,并且如果子部门下还有子部门的话,将其放在该部门后面
     *
     * @return
     */
    @Override
    public List<ByBDepartment> searchForChild(Integer invalidFlag) {
        FlipFilter flipFilter = new FlipFilter(ByBDepartment.class);
        flipFilter.addSearch(100, Operate.NOTEQUAL, "code");
        flipFilter.addSearch(1, Operate.EQUAL, "parentId");
        flipFilter.addSearch(invalidFlag, Operate.EQUAL, "invalidFlag");
        flipFilter.setSortField("sortNum");
        List<ByBDepartment> byBDepartmentList = this.list(flipFilter);
        List<ByBDepartment> list = new ArrayList<ByBDepartment>();
        for (ByBDepartment byBDepartment : byBDepartmentList) {
            if (byBDepartment.getCode().length() == 6) {
                list.add(byBDepartment);
                Integer id = byBDepartment.getId();
                List<ByBDepartment> bDepartment = byBDepartmentMapper.searchByParentId(id);
                if (bDepartment.size() > 0) {
                    for (ByBDepartment department : bDepartment) {
                        if (department.getInvalidFlag() == invalidFlag) {
                            list.add(department);
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<ByBDepartment> searchForParent(Integer releaseFlag, String departmentCode, Integer invalidFlag) {
        FlipFilter flipFilter = new FlipFilter(ByBDepartment.class);
        flipFilter.addSearch(1, Operate.EQUAL, "parentId");
        flipFilter.addSearch(releaseFlag, Operate.EQUAL, "releaseFlag");
        flipFilter.addSearch(invalidFlag, Operate.EQUAL, "invalidFlag");
        flipFilter.setSortField("sortNum");
        List<ByBDepartment> byBDepartmentList = this.list(flipFilter);
        List<ByBDepartment> list = new ArrayList<ByBDepartment>();
        for (ByBDepartment byBDepartment : byBDepartmentList) {
            String code = byBDepartment.getCode();
            if (!departmentCode.equals(code)) {
                list.add(byBDepartment);
            }
        }
        //测试动态sql拼接
//        List<ByBDepartment> list = byBDepartmentMapper.searchList(releaseFlag, departmentCode);
//        Page page = new Page(flipFilter, list.size(), list);
        return list;
    }

    @Override
    public List<ByBDepartment> searchForGroup(String code, Integer invalidFlag) {
        Integer id = byBDepartmentMapper.getByCode(code).getId();
        FlipFilter flipFilter = new FlipFilter(ByBDepartment.class);
        flipFilter.addSearch(id, Operate.EQUAL, "parentId");
        flipFilter.addSearch(invalidFlag, Operate.EQUAL, "invalidFlag");
        flipFilter.setSortField("sortNum");
        return this.list(flipFilter);
    }

    /**
     * 查询所有小组
     *
     * @param releaseFlag
     * @return
     */
    @Override
    public List<ByBDepartment> searchForAllGroup(Integer releaseFlag, Integer invalidFlag) {
        //查询一级部门
        FlipFilter flipFilter = new FlipFilter(ByBDepartment.class);
        flipFilter.addSearch(releaseFlag, Operate.EQUAL, "releaseFlag");
        flipFilter.addSearch(1, Operate.EQUAL, "parentId");
        flipFilter.addSearch(invalidFlag, Operate.EQUAL, "invalidFlag");
        List<ByBDepartment> byBDepartmentList = this.list(flipFilter);
        List<ByBDepartment> byBDepartment1 = new ArrayList<>();
        for (ByBDepartment byBDepartment : byBDepartmentList) {
            List<ByBDepartment> bDepartment = byBDepartmentMapper.searchByParentId(byBDepartment.getId());
            for (ByBDepartment department : bDepartment) {
                if (department.getInvalidFlag() == invalidFlag) {
                    byBDepartment1.add(department);
                }
            }
        }
        return byBDepartment1;
    }

    @Transactional
    @Override
    public void updateDepartmentInvalid(Integer id) {
        ByBDepartment byBDepartment = this.get(id);
        String code = byBDepartment.getCode();
        FlipFilter flipFilter = new FlipFilter(ByBDepartment.class);
        flipFilter.addSearch(code + "%", Operate.LIKE, "code");
        List<ByBDepartment> byBDepartmentList = this.list(flipFilter);
        for (ByBDepartment bDepartment : byBDepartmentList) {
            Integer pid = byBDepartment.getId();
            this.remove(pid);
        }
    }
}