package club.map.base.redis;

/**
 * Create with IntelliJ IDEA
 * Project Name: ics_core
 * Package Name: club.emergency.base.redis
 * Date: 5/23/18 16:24
 * User: 赵金雄
 * Description: redis键名列表类,redis中所有缓存时效统一为6小时,在yml文件中配置
 */
public class RedisKey {
    //子字典表
    public static final String BY_B_BICTIONARY_CHILD = "by_b_dictionary_child";
    //父字典表
    public static final String BY_B_BICTIONARY_PARENT = "by_b_dictionary_parent";
    //部门表
    public static final String BY_B_DEPARTMENT = "by_b_department";
}
