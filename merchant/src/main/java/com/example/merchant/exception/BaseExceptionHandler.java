package com.example.merchant.exception;

import com.example.common.contract.exception.DefineException;
import com.example.common.util.ReturnJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

/**
 * 自定义的公共异常处理器
 * 1.声明异常处理器
 * 2.对异常统一处理
 */
@ControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    /**
     * 处理DTO参数判断抛出的异常ConstraintViolationException
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ReturnJson exceptionHandler(ConstraintViolationException e) {
        return ReturnJson.error(e.getMessage().replaceAll("[^\\u4e00-\\u9fa5]",""));
    }

    /**
     * 处理DTO参数判断抛出的异常MethodArgumentNotValidException
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ReturnJson exceptionHandler(MethodArgumentNotValidException e) {
        return ReturnJson.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 处理开发者主动抛出的异常CommonException
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public ReturnJson exceptionHandler(CommonException e) {
        return ReturnJson.error(e.getResponseCode(),e.getResponseMsg());
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ReturnJson exceptionHandler(HttpRequestMethodNotSupportedException e){
        return ReturnJson.error("请求异常被拦截");
    }

    /**
     * 处理非以上异常问题
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ReturnJson exceptionHandler(Exception e) {
        log.error(e+"");
        return ReturnJson.error("服务异常，请稍后重试");
    }

    /**
     * 处理e签包的异常信息
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = DefineException.class)
    public ReturnJson signAContractException(DefineException e) {
        log.error(e.getE()+":"+e.getE().getMessage());
        return ReturnJson.error(e.getE().getMessage());
    }

}
