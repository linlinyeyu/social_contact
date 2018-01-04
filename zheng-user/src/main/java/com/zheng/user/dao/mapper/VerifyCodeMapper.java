package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.VerifyCode;
import org.apache.ibatis.annotations.Param;

public interface VerifyCodeMapper {
    int deleteByPrimaryKey(Integer verifyId);

    int insert(VerifyCode record);

    int insertSelective(VerifyCode record);

    VerifyCode selectByPrimaryKey(Integer verifyId);

    int updateByPrimaryKeySelective(VerifyCode record);

    int updateByPrimaryKey(VerifyCode record);

    VerifyCode selectByPhone(@Param("phone") String phone, @Param("expireTime") long expireTime, @Param("type") String type);

    //寻找已经被激活且在一定时间内的验证码记录，如果存在，则可执行分步的操作，如注册，第一步验证验证码，第二步则需要验证验证码是否已被激活使用过
    VerifyCode isActive(@Param("phone") String phone,@Param("expireTime") long expireTime,@Param("type") String type);
}