package club.emergency.controller;

import club.emergency.Util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Description: 统计信息管理
 * @Author: lxl
 * @CreateDate: 2019/05/29
 * @Version: v1.0
 */
@Api("测试接口")
@CrossOrigin
@RestController
@RequestMapping("/interface")
public class TestController {

    @ApiOperation("查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "limit", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "name", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "password", dataType = "string", paramType = "query")
    })
    @PostMapping("/test")
    public ResultJson flipList(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "limit", defaultValue = "") Integer limit,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "password", defaultValue = "") String password
    )  {
        String sname =name;
        String spassword =password;
        ResultJson resultJson=new ResultJson();
        List<Map<String,Object>> list =new ArrayList<>();
        Map<String,Object> map= null;
        Random random=new Random();
        for (int i = 0; i < limit ; i++) {
            map= new HashMap<>();

            map.put("id",random.nextInt(100));
            map.put("username","user-"+random.nextInt(100));
            map.put("sex","女");
            map.put("city","城市-"+random.nextInt(100));
            list.add(map);
        }
        resultJson.setCode(0);
        resultJson.setCount(101);
        resultJson.setData(list);
        resultJson.setMsg("");
        return resultJson;
    }
    @ApiOperation("增删改")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
    })
    @PostMapping("/add_update_del")
    public ResultJson addUpdate() {
        ResultJson resultJson=new ResultJson();
        resultJson.setCode(0);
        resultJson.setCount(1);
        resultJson.setData(null);
        resultJson.setMsg("操作成功");
        return resultJson;
    }
//    @ApiOperation("当月任务统计")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "regionCode", value = "区域code", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "recordTypeId", value = "项目类型id", dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "departmentCode", value = "部门code", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "workGroupCode", value = "小组code", dataType = "string", paramType = "query")
//    })
//    @PostMapping("/flipList")
//    public Result flipList(
//            @RequestParam(value = "year", required = false, defaultValue = "1") Integer year,
//            @RequestParam(value = "month", required = false, defaultValue = "10") Integer month,
//            @RequestParam(value = "regionCode", defaultValue = "") String regionCode,
//            @RequestParam(value = "recordTypeId", defaultValue = "") String recordTypeId,
//            @RequestParam(value = "departmentCode", defaultValue = "") String departmentCode,
//            @RequestParam(value = "workGroupCode", defaultValue = "") String workGroupCode
//
//    ) {
//        Page page = byBStoreRecordManager.search(storeroomCode, fileNumber, recordTypeName, departmentName, recordState, pageNo, pageSize);
//        return Result.ok(page);
//    }
}
