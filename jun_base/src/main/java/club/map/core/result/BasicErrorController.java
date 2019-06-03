package club.map.core.result;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 拦截404错误
 * @Author: lxl
 * @CreateDate: 2019/05/22
 * @Version: v1.0
 */
public class BasicErrorController implements ErrorController {
    private static final String ERROR_PATH="/error";
    @RequestMapping("/error")
    public ResponseBean<Object> handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode==404){
            return new ResponseBean<>(false,null,ResultEnums.NOT_FOUND);
        }else {
            return new ResponseBean<>(false,null,ResultEnums.METHOD_NOT_ALLOWED);
        }

    }
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
