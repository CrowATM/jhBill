package cn.bx.core.response;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description Response响应体工具
 * @Author ZK
 * @Date 2023/3/16 18:26
 */
public class ResponseDataUtil {

    /**
     * 写入响应
     * @param response resp
     * @throws IOException
     */
    public static void writeResponseByCodeEnum(HttpServletResponse response, ResponseData<Object> responseData) throws IOException {
//        ResponseData<Object> responseData = new ResponseData<>(responseCodeEnum);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JSON.toJSONString(responseData));
    }

}
