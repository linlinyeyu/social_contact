package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterRelationType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UcenterRelationTypeMapper {
    int deleteByPrimaryKey(Byte relationType);

    int insert(UcenterRelationType record);

    int insertSelective(UcenterRelationType record);

    UcenterRelationType selectByPrimaryKey(Byte relationType);

    int updateByPrimaryKeySelective(UcenterRelationType record);

    int updateByPrimaryKey(UcenterRelationType record);

}