package club.map.base.redis;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Create with IntelliJ IDEA
 * Project Name: DAXT
 * Package Name: club.emergency.base.redis
 * Date: 3/20/19 11:38
 * User: 赵金雄
 * Description: 全局参数设置
 */
@Data
@Component
public class SystemPropertiesConfig {
    //时间是天
    public static Integer dataTimeout = 7;
    public static Integer loginTimeout = 1;
}
