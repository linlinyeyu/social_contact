package com.zheng.api.server.aop;

import java.lang.annotation.*;

/**
 * Created by acer on 2017/12/20.
 */
@Documented
@Target({ ElementType.TYPE,ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateLoginAspect {
}
