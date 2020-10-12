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

//    //处理shiro没有登陆时抛出的异常UnauthorizedException
//    @ResponseBody
//    @ExceptionHandler(UnauthenticatedException.class)
//    public ResponseData unauthenticatedHandler() {
//        return ResponseData.responseData(ResponseCode.TOKENFAILURE);
//    }
//
//    //处理shiro没有授权时抛出的异常UnauthorizedException
//    @ResponseBody
//    @ExceptionHandler(UnauthorizedException.class)
//    public ResponseData unauthorizedHandler() {
//        return ResponseData.responseData(ResponseCode.ACCESSFORBIDDEN);
//    }

    //处理DTO参数判断抛出的异常ConstraintViolationException
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ReturnJson exceptionHandler(ConstraintViolationException e) {
        return ReturnJson.error(e.getMessage().replaceAll("[^\\u4e00-\\u9fa5]",""));
    }

    //处理DTO参数判断抛出的异常MethodArgumentNotValidException
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ReturnJson exceptionHandler(MethodArgumentNotValidException e) {
        return ReturnJson.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    //处理开发者主动抛出的异常CommonException
    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public ReturnJson exceptionHandler(CommonException e) {
        return ReturnJson.error(e.getResponseCode(),e.getResponseMsg());
    }

    @ResponseBody
    @ExceptionHandler(IOException.class)
    public ReturnJson exceptionHandler(IOException e){
        return ReturnJson.error("文件上传失败！");
    }

    //shiro为登录时
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ReturnJson exceptionHandler(HttpRequestMethodNotSupportedException e){
        return ReturnJson.error("请先登录后再进行操作");
    }

    //处理非以上异常问题
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ReturnJson exceptionHandler(Exception e) {
        log.error(e.toString()+":"+e.getMessage());
        return ReturnJson.error("服务内部错误");
    }

    @ResponseBody
    @ExceptionHandler(value = DefineException.class)
    public ReturnJson signAContractException(DefineException e) {
        log.error(e.toString()+":"+e.getMessage());
        return ReturnJson.error(e.getMessage());
    }

}
