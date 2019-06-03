
package club.emergency.controller;

import club.map.base.util.CoordinateTransformationUtil;
import club.map.core.web.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2019-04-08
 * Time: 15:44
 * Description:
 */
@Api("坐标转换管理")
@CrossOrigin
@RestController
@RequestMapping("/coordinate")
public class CoordinateTransController {

    @ApiOperation("WGS84转CJ(城建)坐标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x", value = "经度", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "y", value = "纬度", dataType = "string", paramType = "query")
    })
    @PostMapping("/transToCJ")
    public Result transToCJ(
            @RequestParam(value = "x", defaultValue = "") String x,
            @RequestParam(value = "y", defaultValue = "") String y
    ) {
        if (!"".equals(x) && !"".equals(y)) {
            String str = x + "," + y;
            String coordinate = CoordinateTransformationUtil.TranspantWGS842XACJ(str);
            return Result.ok(coordinate);
        } else {
            return Result.error("x,y不能为空");
        }
    }

    @ApiOperation("CJ(城建)转WGS84坐标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x", value = "x坐标", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "y", value = "y坐标", dataType = "string", paramType = "query")
    })
    @PostMapping("/transToWGS")
    public Result transToWGS(
            @RequestParam(value = "x", defaultValue = "") String x,
            @RequestParam(value = "y", defaultValue = "") String y
    ) {
        if (!"".equals(x) && !"".equals(y)) {
            String str = x + "," + y;
            String coordinate = CoordinateTransformationUtil.TranspantXACJ2WGS84(str);
            return Result.ok(coordinate);
        } else {
            return Result.error("x,y不能为空");
        }
    }
}
