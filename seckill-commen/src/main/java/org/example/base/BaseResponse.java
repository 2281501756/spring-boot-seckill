package org.example.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.exception.ErrorMessage;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;


    public BaseResponse(T data) {
        this.data = data;
    }

    public static BaseResponse<Object> ok(Object o) {
        return new BaseResponse<Object>(0, "成功", o);
    }

    public static <T> BaseResponse<T> OK(T t) {
        return new BaseResponse<T>(0, "成功", t);
    }

    public static BaseResponse<String> error(ErrorMessage errorMessage) {
        return new BaseResponse<String>(errorMessage.getCode(), errorMessage.getMessage(), null);
    }

    public static <T> BaseResponse<T> error(ErrorMessage errorMessage, T data) {
        return new BaseResponse<T>(errorMessage.getCode(), errorMessage.getMessage(), data);
    }

    public static BaseResponse<String> error(Integer code, String message) {
        return new BaseResponse<String>(code, message, null);
    }

    public static <T> BaseResponse<T> error(Integer code, String message, T data) {
        return new BaseResponse<T>(code, message, data);
    }
}
