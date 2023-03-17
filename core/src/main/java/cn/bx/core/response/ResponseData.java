package cn.bx.core.response;

import cn.bx.core.config.security.support.ResultCode;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Description 自定义json响应体
 * @Author ZK
 * @Date 2023/3/16 18:23
 */
public class ResponseData<T> {
    private Integer code;
    private T data;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseData(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseData() {
    }

    public ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseData(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResponseData(ResultCode resultCode, String ...message) {
        this.code = resultCode.getCode();
        Optional.ofNullable(message).ifPresent(m -> {
            String _message = Arrays.stream(message).filter(StringUtils::isNotBlank).findFirst().orElse(null);
            this.message = _message == null ? resultCode.getMessage() : _message;
        });
    }

}
