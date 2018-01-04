package com.zheng.api.server.aop.aspect;

import com.zheng.common.util.ErrorCodeEnum;
import com.zheng.common.util.FormatResponseUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by acer on 2017/12/20.
 */
@Aspect
public class UserLoginAspect {

    @Pointcut("@annotation(com.zheng.api.server.aop.ValidateLoginAspect)")
    public void userValidate() {
    }

    @Around("userValidate()")
    public Object deBefore(ProceedingJoinPoint pjp){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Object userId = request.getAttribute("userId");

        if(userId == null){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_TOKEN_EXPIRE);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
    }
}
