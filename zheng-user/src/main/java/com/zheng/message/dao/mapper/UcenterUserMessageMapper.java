package com.zheng.message.dao.mapper;

import com.zheng.message.dao.model.UcenterUserMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcenterUserMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UcenterUserMessage record);

    int insertSelective(UcenterUserMessage record);

    UcenterUserMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UcenterUserMessage record);

    int updateByPrimaryKey(UcenterUserMessage record);

    int insertUserMessageList(List<UcenterUserMessage> userMessageList);

}