package com.zheng.api.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zheng.common.base.BaseApiController;
import com.zheng.common.base.BaseResult;
import com.zheng.common.util.*;
import com.zheng.message.platform.DatabaseMessagePlatform;
import com.zheng.message.platform.JPushPlatform;
import com.zheng.message.sdk.MessageClient;
import com.zheng.message.sdk.MessageEntity;
import com.zheng.sms.enums.SmsTemplate;
import com.zheng.user.service.VerifyCodeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import javax.jms.*;

import java.util.Map;

/**
 * Created by acer on 2017/12/14.
 */
@RestController
@RequestMapping("/api/common")
public class CommonController extends BaseApiController {


    @Autowired
    private VerifyCodeService verifyCodeService;

    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping( value = "/sendCode",
            method = RequestMethod.POST,
            consumes = "application/json")
    @ApiOperation("发送验证码")
    public BaseResult sendCode(@RequestBody Map<String, String> map){
        String type = map.get("type");
        String phone = map.get("phone");

        if(StringUtil.isNullOrEmpty(type)){
            logger.error( "类型为空");
            return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
        }
        if(!ValidatorUtil.validPhone(phone)){
            logger.error( phone + "手机格式有误");
            return FormatResponseUtil.error(ErrorCodeEnum.PHONE_NOT_MATCH);
        }
        SmsTemplate smsTemplate = SmsTemplate.getTemplateByName(type);

        if(smsTemplate == null){
            return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
        }
        return verifyCodeService.sendCode(phone, smsTemplate);
    }

    @RequestMapping("/verifyCode/phone/{phone}")
    @ApiOperation("验证验证码")
    public BaseResult verifyCode( @PathVariable String phone, String code, String type){
        SmsTemplate smsTemplate = SmsTemplate.getTemplateByName(type);

        boolean isVerify = verifyCodeService.verify(phone, code, smsTemplate);

        return isVerify ? FormatResponseUtil.formatResponse() : FormatResponseUtil.error(ErrorCodeEnum.FAILED);
    }


    @Autowired
    private MessageClient messageClient;

    @Autowired
    private DatabaseMessagePlatform databaseMessagePlatform;

    @Autowired
    private JPushPlatform jPushPlatform;


    @RequestMapping("/test")
    public Object test(){
        MessageEntity entity = new MessageEntity();
        entity.setPlatforms( jPushPlatform)
                .setTitle("测试")
                .setRegisterTokens("160a3797c8011d8f074")
                .setContent("测试出")
                .setUserIds("13532937");
        messageClient.pushCache(entity);

        return null;
    }




}
