package com.zheng.im.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zheng.common.base.BaseResult;
import com.zheng.common.util.FormatResponseUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linlinyeyu on 2017/12/13.
 */
public class FormatResultUtil {
    public static BaseResult judgeResult(String result){
        if (result != "") {
            Map<String,Object> map = JSON.parseObject(result);
            if ((int)map.get("code") == 200){
                return FormatResponseUtil.formatResponse(0,"请求成功");
            }
            return FormatResponseUtil.formatResponse(-90001,(String)map.get("desc"));
        }else {
            return FormatResponseUtil.formatResponse(-90002,"返回结果为空");
        }
    }

    public static BaseResult judegResultMap(String result){
        if (result == ""){
            return FormatResponseUtil.formatResponse(-90002,"返回结果为空");
        }else {
            Map<String,Object> map = JSON.parseObject(result);
            if ((int)map.get("code") == 200){
                return new BaseResult(0,"请求成功",map);
            }
            return FormatResponseUtil.formatResponse(-90001,(String) map.get("desc"));
        }
    }

    public static BaseResult judgeResult(JSONObject jsonObject){
        if (jsonObject == null){
            return FormatResponseUtil.formatResponse(-90002,"返回结果为空");
        }else {
            if ((int)jsonObject.get("code") == 200){
                return new BaseResult(0,"请求成功",jsonObject);
            }
            return FormatResponseUtil.formatResponse(-90001,(String)jsonObject.get("desc"));
        }
    }
}
