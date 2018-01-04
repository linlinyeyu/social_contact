package com.zheng.user.service;

import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServicePinet;
import com.zheng.user.dao.model.UcenterUserGroup;

/**
* UcenterUserGroupService接口
* Created by pinet on 2017/12/19.
*/
public interface UcenterUserGroupService extends BaseServicePinet<UcenterUserGroup> {
    /**
     * 创建群聊
     * @param uuid
     * @param members
     * @return
     */
    BaseResult createGroup(String uuid,String members,String nickname);

    /**
     * 添加群成员
     * @param uuid
     * @param tid
     * @param members
     * @return
     */
    BaseResult addMembers(String uuid,String tid,String members,String nickname);

    /**
     * 编辑群名称和公告
     * @param ucenterUserGroup
     * @return
     */
    BaseResult updGroup(UcenterUserGroup ucenterUserGroup);

    /**
     * 解散群
     * @param uuid
     * @param tid
     * @return
     */
    BaseResult removeGroup(String uuid,String tid);

    /**
     * 更换群主
     * @param uuid
     * @param tid
     * @param member
     * @return
     */
    BaseResult modifyGroupLeader(String uuid,String tid,String member);

    /**
     * 获取群二维码信息
     * @param tid
     * @return
     */
    BaseResult getGroupQr(String tid,String uuid);

    /**
     * 获取面对面建群人信息
     * @param uuid
     * @param lat
     * @param lng
     * @return
     */
    BaseResult getAroundGroup(String uuid,short password,double lat,double lng);

    /**
     * 退出面对面建群
     */
    BaseResult quitAroundGroup(String uuid,short password,double lat,double lng);

    /**
     * 面对面建群
     * @param uuid
     * @param password
     * @param lat
     * @param lng
     * @return
     */
    BaseResult joinAroundGroup(String uuid,short password,double lat,double lng,String nickname);
}