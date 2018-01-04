package com.zheng.settings.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.common.constants.SettingsConstants;
import com.zheng.common.util.RedisUtil;
import com.zheng.common.util.StringUtil;
import com.zheng.settings.dao.mapper.SettingsMapper;
import com.zheng.settings.dao.model.Settings;
import com.zheng.settings.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
* SettingsService实现
* Created by shuzheng on 2017/12/21.
*/
@Service
@Transactional
@BaseService
public class SettingsServiceImpl extends BaseServiceImplPinet<SettingsMapper, Settings> implements SettingsService {

    private static Logger _log = LoggerFactory.getLogger(SettingsServiceImpl.class);

    @Autowired
    SettingsMapper settingsMapper;


    private Logger logger = LoggerFactory.getLogger(SettingsServiceImpl.class);

    @Override
    public String getByKey(String key, String defaultValue) {
        if(StringUtil.isEmpty(key)){
            throw new RuntimeException("key is not allow null or empty");
        }
        String cacheKey = SettingsConstants.SETTING + key;
        String value = RedisUtil.get(cacheKey);

        if(value != null){
            return value;
        }
        Settings settings = settingsMapper.selectKey(key);
        if(settings == null){
            settings = this.insert(key, defaultValue);
        }
        value = settings.getSettingValue();
        RedisUtil.set(cacheKey, value);
        return value;
    }

    @Override
    public boolean updateKey(String key, String value) {
        if(StringUtil.isEmpty(key)){
            throw new RuntimeException("key is not allow null or empty");
        }
        this.getByKey(key, value);
        Settings settings = new Settings();
        settings.setSettingKey(key);
        settings.setSettingValue(value);
        int num = settingsMapper.updateByPrimaryKeySelective(settings);

        if(num < 1){
            return false;
        }
        RedisUtil.set(SettingsConstants.SETTING + key, value);

        return true;
    }

    @Override
    public <T> T getByKey(String key, T defaultT, Class<T> cls) {
        String defaultValue = JSONObject.toJSONString(defaultT);
        String value = this.getByKey(key, defaultValue);
        return JSONObject.parseObject(value, cls);
    }

    @Override
    public <T> boolean updateKey(String key, T t) {
        return this.updateKey(key, JSONObject.toJSONString(t));
    }


    private Settings insert(String key, String value){
        Settings settings = new Settings();
        settings.setSettingKey(key);
        settings.setSettingValue(value);
        settings.setCreateTime(new Date().getTime());
        settingsMapper.insertSelective(settings);
        return settings;
    }

}