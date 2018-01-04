package com.zheng.common.util;

import com.alibaba.fastjson.JSON;
import com.zheng.common.base.BaseResult;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by linlinyeyu on 2017/12/14.
 */
public class FormatResponseUtil {
    public static BaseResult formatResponse(){
        BaseResult baseResult = null ;
        return formatResponse(baseResult);
    }

    public static BaseResult formatResponse(int code, String message){
        return new BaseResult(code, message,null);
    }

    public static BaseResult error(ErrorCodeEnum errorCodeEnum){
        BaseResult baseResult = new BaseResult(errorCodeEnum.getKey(), errorCodeEnum.getValue());
        return baseResult;
    }

    public static BaseResult formatResponse(Object obj){
        BaseResult baseResult = new BaseResult(0,"请求成功",obj);
        return baseResult;
    }

    public static BaseResult formatResponse(BaseResult baseResult){
        if(baseResult == null){
            baseResult = new BaseResult(0,"请求成功",null);
        }
        return baseResult;
    }

    public static boolean isSuccess(BaseResult baseResult){
        return baseResult != null && baseResult.getCode() == 0;
    }

    public static <T> BaseResult formatResponseDomain(T t) {
        BaseResult baseResult = new BaseResult(0,"请求成功",t);
        return baseResult;
    }

    public static void writeResponse(ErrorCodeEnum errorCodeEnum, HttpServletResponse response) throws IOException{
        writeResponse(errorCodeEnum.getKey(), errorCodeEnum.getValue(), response);
    }

    public static void writeResponse(int code, String message, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.append(JSON.toJSONString(new BaseResult(code, message))).flush();

//        ServletOutputStream writer = response.getOutputStream();
//        writer.write(JSON.toJSONString(new BaseResult(code, message)).getBytes());
    }
}
