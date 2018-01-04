package com.zheng.api.server.controller;

import com.zheng.common.base.BaseController;
import com.zheng.im.base.ChatBase;
import com.zheng.im.base.UserBase;
import com.zheng.im.domain.user.Friend;
import com.zheng.oss.upload.Upload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * 单点登录管理
 * Created by shuzheng on 2016/12/10.
 */
@Controller
@RequestMapping("/api/home")
@Api(value = "api接口", description = "api接口")
public class HomeController extends BaseController {

    @ApiOperation(value = "认证中心首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        System.out.println("asdfafaf");
        return new ModelAndView("index.jsp");
    }





}