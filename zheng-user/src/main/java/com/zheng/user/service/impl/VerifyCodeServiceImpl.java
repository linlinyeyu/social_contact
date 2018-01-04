package com.zheng.user.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.common.util.*;
import com.zheng.sms.enums.SmsTemplate;
import com.zheng.sms.factory.SmsFactory;
import com.zheng.user.dao.mapper.VerifyCodeMapper;
import com.zheng.user.dao.model.VerifyCode;
import com.zheng.user.service.VerifyCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
* VerifyCodeService实现
* Created by shuzheng on 2017/12/15.
*/
@Service
@Transactional
@BaseService
public class VerifyCodeServiceImpl extends BaseServiceImplPinet<VerifyCodeMapper, VerifyCode> implements VerifyCodeService {

    private static Logger _log = LoggerFactory.getLogger(VerifyCodeServiceImpl.class);

    @Autowired
    VerifyCodeMapper verifyCodeMapper;

    public boolean verify(String phone, String code, SmsTemplate smsTemplate){

        //验证码为空
        if(StringUtil.isEmpty(code)){
            return false;
        }
        long expireTime = TimeUtil.timeMillsChange(System.currentTimeMillis(), Calendar.MINUTE, -15);
        VerifyCode verifyCode = verifyCodeMapper.selectByPhone(phone, expireTime, smsTemplate.getName());

        //验证码找不到或者过期
        if(verifyCode == null){
            return false;
        }

        //验证码不相等
        if(!code.equals(verifyCode.getCode())){
            return false;
        }

        //更新激活时间
        VerifyCode update = new VerifyCode();
        update.setVerifyId(verifyCode.getVerifyId());
        update.setUpdateTime(new Date().getTime());
        update.setStatus(1);
        verifyCodeMapper.updateByPrimaryKeySelective(update);
        return true;
    }

    @Override
    public BaseResult sendCode(String phone, SmsTemplate smsTemplate){

        if(!ValidatorUtil.validPhone(phone)){
            return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
        }

        String min = "15";
        int code = StringUtil.randomSixCode();

        Map<String, String> data = WowCollections.map("code", code + "", "min", min);


        BaseResult baseResult = SmsFactory.create().send(phone, smsTemplate,data);

        if(FormatResponseUtil.isSuccess(baseResult)){
            VerifyCode verifyCode = new VerifyCode();
            verifyCode.setCode(code + "");
            verifyCode.setCreateTime(new Date().getTime());
            verifyCode.setPhone(phone);
            verifyCode.setStatus(0);
            verifyCode.setType(smsTemplate.getName());
            this.insertSelective(verifyCode);
        }

        return baseResult;
    }

    public boolean isActive(String phone, SmsTemplate smsTemplate){
        if(!ValidatorUtil.validPhone(phone)){
            return false;
        }
        int activeTime = smsTemplate.getActiveTime();
        long expireTime = TimeUtil.timeMillsChange(System.currentTimeMillis(), Calendar.MINUTE, -1 * activeTime);
        VerifyCode verifyCode = verifyCodeMapper.selectByPhone(phone, expireTime, smsTemplate.getName());
        boolean isActive = verifyCode != null ;

        return isActive;
    }

}