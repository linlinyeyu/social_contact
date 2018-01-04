package com.zheng.oss.common.constant;

import com.zheng.common.base.BaseResult;

/**
 * oss系统常量枚举类
 * Created by shuzheng on 2017/4/18.
 */
public class SmsResult extends BaseResult {

    public SmsResult(com.zheng.oss.common.constant.SmsResultConstants smsResultConstants, Object data) {
        super(smsResultConstants.getCode(), smsResultConstants.getMessage(), data);
    }

    public SmsResult(com.zheng.oss.common.constant.SmsResultConstants smsResultConstants) {
        super(smsResultConstants.getCode(), smsResultConstants.getMessage(), null);
    }

}
