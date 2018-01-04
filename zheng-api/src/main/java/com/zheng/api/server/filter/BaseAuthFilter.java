package com.zheng.api.server.filter;

import com.zheng.common.constants.CacheConstants;
import com.zheng.common.util.*;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by acer on 2017/12/15.
 */
public class BaseAuthFilter extends OncePerRequestFilter{

    private boolean isAuth = false;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isAuth){
            if(request.getMethod().toUpperCase().equals("POST")){
                MyRequestWrapper wrapper = new MyRequestWrapper(request);
                String body = wrapper.getBody();
                String curTime = request.getHeader("timestamp");
                String nonce = request.getHeader("nonce");
                String sign = request.getHeader("sign");
                if(StringUtil.isEmpty(curTime) || StringUtil.isEmpty(nonce)){
                    FormatResponseUtil.writeResponse(ErrorCodeEnum.SIGN_ERROR, response);
                    return;
                }
                String mySign = MD5Util.MD5(curTime + nonce + body);
                System.out.println("timestamp:" + curTime + ";nonce:" + nonce + ";body:" + body);
                System.out.println("mySign:" + mySign + ";sign:" + sign);
                if(!mySign.equals(sign)){
                    FormatResponseUtil.writeResponse(ErrorCodeEnum.SIGN_ERROR, response);
                    return;
                }
                filterChain.doFilter(wrapper, response);
                return;
            }
        }


        filterChain.doFilter(request, response);
    }
}
