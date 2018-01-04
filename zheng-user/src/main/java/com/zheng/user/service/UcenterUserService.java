package com.zheng.user.service;

import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServicePinet;
import com.zheng.user.dao.model.UcenterUser;
import com.zheng.user.model.UcenterUserVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
* UcenterUserService接口
* Created by pinet on 2017/12/14.
*/
public interface UcenterUserService extends BaseServicePinet<UcenterUser> {

    BaseResult loginPasswd(String phone, String password);

    UcenterUser selectById(Integer id);

    UcenterUser selectByUuid(String uuid);

    UcenterUser selectByPhone(String phone);

    UcenterUser selectByToken(String token);


    /**
     * 传给APP端自身的详情信息数据，如登录，注册等
     * */
    Map<String, Object> selectAppUserDetail(Integer userId);


    int update(UcenterUser ucenterUser);


    int updateAndClearCache(UcenterUser ucenterUser);


    /**
     * 注册账号
     * */
    BaseResult regist(UcenterUser ucenterUser);

    /**
     * 登录账号
     * */
    Map<String, Object> login(int userId, String ip, String deviceId, String agent, int terminal,
                              String jpushToken);

    List<UcenterUser> selectUsersByUuid(List<String> uuids);

    /**
     * 根据uuid查询用户信息，并封装成一个map形式
     * 如uuid为1，则返回格式为key => 1, value => user
     * @param simpleLevel 简化程度，最低为1，越低得到的信息越少
     * */
    Map<String, UcenterUser> selectUsersByUuidWrappedMap(List<String> uuids, int simpleLevel);

    /**
     * 查找好友对应的备注，如果找到了则使用备注，否则使用昵称
     * */
    Map<String, UcenterUserVO> selectFriendsByUuidWrappedMap(String uuid, List<String> uuids, int simpleLevel);
    //测试bean
    String selectName(String uuid);

    /**
     * 搜索好友
     * @param mobile
     * @return
     */
    BaseResult selectFriend(String mobile,String uuid);

    /**
     * 扫描手机联系人
     * @param uuid
     * @param phones
     * @return
     */
    BaseResult matchMailList(String uuid,String phones);
}