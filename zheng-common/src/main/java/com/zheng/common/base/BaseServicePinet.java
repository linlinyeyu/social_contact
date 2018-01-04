package com.zheng.common.base;

/**
 * Created by acer on 2017/12/14.
 */
public interface BaseServicePinet<Record> {

    int deleteByPrimaryKey(Integer id);

    int insert(Record record);

    int insertSelective(Record record);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);

    int deleteByPrimaryKeys(String ids);

    Record selectByPrimaryKey(Integer id);

    void initMapper();
}
