package club.emergency.dictionary.manager.impl;

import club.emergency.dictionary.manager.ByBDictionaryChildManager;
import club.emergency.dictionary.mapper.ByBDictionaryChildMapper;
import club.emergency.dictionary.mapper.ByBDictionaryParentMapper;
import club.emergency.dictionary.model.ByBDictionaryChild;
import club.map.base.redis.RedisKey;
import club.map.base.redis.SystemPropertiesConfig;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.FlipFilter;
import club.map.core.model.Operate;
import club.map.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ByBDictionaryChildManagerImpl extends GenericManagerImpl<ByBDictionaryChild, Integer> implements ByBDictionaryChildManager {

    private ByBDictionaryParentMapper byBDictionaryParentMapper;


    @Autowired
    public ByBDictionaryChildManagerImpl(ByBDictionaryChildMapper mapper,
                                         ByBDictionaryParentMapper byBDictionaryParentMapper
    ) {
        super(mapper, ByBDictionaryChild.class);
        this.byBDictionaryParentMapper = byBDictionaryParentMapper;
    }

    @Override
    public List<ByBDictionaryChild> searchByDictionaryCode(String dictionaryCode) {
        FlipFilter flipFilter = new FlipFilter(ByBDictionaryChild.class);
        flipFilter.addSearch(dictionaryCode, Operate.EQUAL, "dictionaryCode");
        List<ByBDictionaryChild> byBDictionaryChildList = this.list(flipFilter);
        return byBDictionaryChildList;
    }

    @Transactional
    @Override
    public void upperSave(ByBDictionaryChild byBDictionaryChild) {
        this.save(byBDictionaryChild);
    }

    @Transactional
    @Override
    public void removeByIds(String ids) {
        String[] idArr = ids.split(",");
        Arrays.asList(idArr).forEach(id -> {
            Integer pid = Integer.valueOf(id);
            //删除单个数据
            this.remove(pid);
        });
    }

    @Override
    public Page searchChild(String alias, String dictionaryCode, Integer pageNo, Integer pageSize) {
        FlipFilter flipFilter = new FlipFilter(ByBDictionaryChild.class);
        flipFilter.addSearch("%" + alias + "%", Operate.LIKE, "alias");
        flipFilter.addSearch(dictionaryCode + "%", Operate.LIKE, "dictionaryCode");
        flipFilter.updatePageNo(pageNo);
        flipFilter.setPageSize(pageSize);
        Page page = this.flipUsingInPage(flipFilter);
        List<ByBDictionaryChild> byBDictionaryChildList = page.getListInfo();
        for (ByBDictionaryChild byBDictionaryChild : byBDictionaryChildList) {
            String code = byBDictionaryChild.getDictionaryCode();
            byBDictionaryChild.setDictionaryName(byBDictionaryParentMapper.searchByCode(code).getAlias());
        }
        return page;
    }

    @Override
    public List<ByBDictionaryChild> searchByParentAlias(String alias) {
        //根据父表成果类型名称获取code
        String code = byBDictionaryParentMapper.searchByAlias(alias);
        FlipFilter flipFilter = new FlipFilter(ByBDictionaryChild.class);
        flipFilter.addSearch(code, Operate.EQUAL, "dictionaryCode");
        List<ByBDictionaryChild> byBDictionaryChildList = this.list(flipFilter);
        return byBDictionaryChildList;
    }

    @Override
    public List<ByBDictionaryChild> searchLessNowYearByDictionaryCode(String dictionaryCode) {
        FlipFilter flipFilter = new FlipFilter(ByBDictionaryChild.class);
        flipFilter.addSearch(dictionaryCode, Operate.EQUAL, "dictionaryCode");
        List<ByBDictionaryChild> byBDictionaryChildList = this.list(flipFilter);
        LocalDateTime localDateTime = LocalDateTime.now();
        int nowYear = localDateTime.getYear();
        byBDictionaryChildList.removeIf(e -> compare(e.getAlias(), nowYear));
        return byBDictionaryChildList;
    }

    /**
     * 判断字典值年份是否大于当前年份
     *
     * @param year    字典值年份
     * @param nowYear 当前年份
     * @return
     */
    private boolean compare(String year, int nowYear) {
        boolean flag = false;
        if (Integer.valueOf(year) > nowYear) {
            flag = true;
        }
        return flag;
    }
}