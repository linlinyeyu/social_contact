package com.zheng.im.base;

import com.zheng.common.base.BaseResult;
import com.zheng.im.domain.user.Friend;
import com.zheng.im.domain.user.User;

import java.util.Map;

/**
 * Created by linlinyeyu on 2017/12/12.
 */
 public interface UserBase {
    /**
     * 注册即时通信
     * @param user
     * @return
     */
     BaseResult imRegister(User user);

    /**
     * 添加好友
     * @param friend
     * @return
     */
     BaseResult addFriend(Friend friend);

    /**
     * 获取好友关系
     * @return
     */
     BaseResult getFriends(Friend friend);

    /**
     * 获取用户名片
     * @param accid
     * @return
     */
     BaseResult getUserCard(String[] accid);

    /**
     * 通信id更新
     * @param accid
     * @return
     */
     BaseResult updUserId(String accid);

    /**
     * 更新并获取新的token
     * @param accid
     * @return
     */
     BaseResult updAndGetUser(String accid);

    /**
     * 解禁id
     * @param accid
     * @return
     */
     BaseResult unBlockUser(String accid);

    /**
     * 封禁id
     * @param accid
     * @return
     */
     BaseResult blockUser(String accid);

    /**
     * 更新用户名片
     * @param user
     * @return
     */
     BaseResult udpUser(User user);

    /**
     * 设置桌面端是否推送移动端
     * @param accid
     * @param donnopOpen
     * @return
     */
     BaseResult setDeskToApp(String accid,String donnopOpen);

    /**
     * 更新好友关系

     * @return
     */
     BaseResult updFriend(Friend friend);

    /**
     * 删除好友
     * @return
     */
     BaseResult delFriend(Friend friend);

    /**
     * 设置黑名单或静音
     * @param accid
     * @param targetAcc
     * @param relationType
     * @param value
     * @return
     */
     BaseResult setSpecialRelation(String accid,String targetAcc,int relationType,int value);

    /**
     * 获取黑名单列表
     * @param accid
     * @return
     */
     BaseResult getSpecialRelationList(String accid);
}
