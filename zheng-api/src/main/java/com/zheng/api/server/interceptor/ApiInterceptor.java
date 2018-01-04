package com.zheng.api.server.interceptor;

import com.alibaba.fastjson.JSON;
import com.zheng.common.constants.CacheConstants;
import com.zheng.common.util.*;
import com.zheng.user.dao.model.UcenterUser;
import com.zheng.user.service.UcenterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

import static com.zheng.common.util.RedisUtil.getObject;

/**
 * Created by acer on 2017/12/15.
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private UcenterUserService ucenterUserService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取token
        String token = request.getHeader("token");
        if(!StringUtil.isEmpty(token)){
            Integer userId = RedisUtil.getObject(CacheConstants.USER_TOKEN + token, Integer.class);
            UcenterUser user = null;
            if(userId == null){
                user = ucenterUserService.selectByToken(token);
                if(user == null || user.getTokenExpireTime() <= System.currentTimeMillis()){
                    FormatResponseUtil.writeResponse(ErrorCodeEnum.USER_TOKEN_EXPIRE, response);
                    return false;
                }
                userId = user.getUserId();
                RedisUtil.setObject(CacheConstants.USER_TOKEN + token, userId);
                RedisUtil.setObject(CacheConstants.USER + userId, user);
            }else{
                //如果userId不为空则取缓存的object
                user = RedisUtil.getObject(CacheConstants.USER + userId, UcenterUser.class);
            }

            //如果有userId但没有user，则执行一遍获取user的操作并保存到缓存中
            if(user == null){
                user = ucenterUserService.selectByToken(token);
                if(user == null || user.getTokenExpireTime() <= System.currentTimeMillis()){
                    FormatResponseUtil.writeResponse(ErrorCodeEnum.USER_TOKEN_EXPIRE, response);
                    return false;
                }
                RedisUtil.setObject(CacheConstants.USER + userId, user);
            }
            UcenterUser update = new UcenterUser();
            update.setUserId(user.getUserId());
            update.setTokenExpireTime(TimeUtil.timeMillsChange(System.currentTimeMillis(), Calendar.DAY_OF_MONTH, 10));
            //更新数据库过期时间
            ucenterUserService.update(update);

            request.setAttribute("user", user);
            request.setAttribute("userId", user.getUserId());
            request.setAttribute("uuid", user.getUuid());

        }


        return super.preHandle(request, response, handler);
    }
}
