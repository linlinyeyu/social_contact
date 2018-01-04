package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterUser;
import com.zheng.user.model.UcenterUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UcenterUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UcenterUser record);

    int insertSelective(UcenterUser record);

    UcenterUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UcenterUser record);

    int updateByPrimaryKey(UcenterUser record);

    UcenterUser selectByPhone(@Param("phone") String phone);

    UcenterUser selectByToken(@Param("token") String token);

    Map<String, Object> selectAppDetail(@Param("userId") int userId);

    int selectByUuid(String uuid);

    String selectName(String uuid);

    List<UcenterUser> selectUsersByUuid(@Param("list") List<String> uuids, @Param("simpleLevel") int simpleLevel);

    List<UcenterUserVO> selectFriendsByUuid(@Param("uuid") String uuid, @Param("list") List<String> uuids, @Param("simpleLevel") int simpleLevel);

    UcenterUser selectBeanByUuid(String uuid);



    List<Map<String,Object>> selectByPhoneList(@Param("phones") List<String> phones,@Param("uuid") String uuid);
}