package com.zheng.settings.service;

import com.zheng.common.base.BaseServicePinet;
import com.zheng.settings.dao.model.Settings;

/**
* SettingsService接口
* Created by pinet on 2017/12/21.
*/
public interface SettingsService extends BaseServicePinet<Settings> {

    String getByKey(String key, String defaultValue);

    boolean updateKey(String key, String value);

    <T> T getByKey(String key, T t, Class<T> cls);


    <T> boolean updateKey(String key, T t);

}