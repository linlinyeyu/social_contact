package com.zheng.settings.dao.mapper;

import com.zheng.settings.dao.model.Settings;

public interface SettingsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Settings record);

    int insertSelective(Settings record);

    Settings selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Settings record);

    int updateByPrimaryKey(Settings record);


    Settings selectKey(String key);
}