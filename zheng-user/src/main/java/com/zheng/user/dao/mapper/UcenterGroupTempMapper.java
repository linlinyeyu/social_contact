package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterGroupTemp;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UcenterGroupTempMapper {
    int deleteByPrimaryKey(Integer GroupTempId);

    int insert(UcenterGroupTemp record);

    int insertSelective(UcenterGroupTemp record);

    UcenterGroupTemp selectByPrimaryKey(Integer GroupTempId);

    int updateByPrimaryKeySelective(UcenterGroupTemp record);

    int updateByPrimaryKey(UcenterGroupTemp record);

    List<Map<String,Object>> selectByPasswordAndGeo(UcenterGroupTemp record);

    int selectUserByPg(UcenterGroupTemp record);

    int deleteByPug(UcenterGroupTemp record);
}
