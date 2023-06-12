package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.base.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Object> handlerArgumentNotValidException(MethodArgumentNotValidException
                                                                         exception) {
        return handlerNotValidException(exception);
    }


    @ExceptionHandler(BindException.class)
    public BaseResponse<Object> handlerBindException(BindException exception) {
        return handlerNotValidException(exception);
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<String> exception(Throwable throwable) {
        log.error("系统异常", throwable);
        return BaseResponse.error(306, "服务器太忙碌了~让它休息一会吧!");

    }

    public BaseResponse<Object> handlerNotValidException(Exception e) {
        log.debug("begin resolve argument exception");
        BindingResult result;
        if (e instanceof BindException) {
            BindException exception = (BindException) e;
            result = exception.getBindingResult();
        } else {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            result = exception.getBindingResult();
        }

        Map<String, Object> maps;
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            maps = new HashMap<>(fieldErrors.size());
            fieldErrors.forEach(error -> {
                maps.put(error.getField(), error.getDefaultMessage());
            });
        } else {
            maps = Collections.EMPTY_MAP;
        }

        //此处的错误码应该定义各枚举类，错误码都在枚举文件中，方便后续统一处理，此处只是演示，就忽略了
        return BaseResponse.error(400, "参数错误", maps);

    }
}