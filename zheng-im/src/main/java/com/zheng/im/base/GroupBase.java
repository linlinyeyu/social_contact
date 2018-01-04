package com.zheng.im.base;

import com.zheng.common.base.BaseResult;
import com.zheng.im.domain.group.AddUserGroup;
import com.zheng.im.domain.group.CreateGroup;
import com.zheng.im.domain.group.EditGroup;

import java.util.Map;

/**
 * Created by linlinyeyu on 2017/12/13.
 */
public interface GroupBase {
    /**
     * 创建群
     * @param createGroup
     * @return
     */
    BaseResult createGroup(CreateGroup createGroup);

    /**
     * 拉人入群
     * @param addUserGroup
     * @return
     */
    BaseResult addUserGroup(AddUserGroup addUserGroup);

    /**
     * 踢人出群
     * @param tid
     * @param owner
     * @param member
     * @return
     */
    BaseResult kickUser(String tid,String owner,String member);

    /**
     * 解散群
     * @param tid
     * @param owner
     * @return
     */
    BaseResult remove(String tid,String owner);

    /**
     * 编辑群资料
     * @param editGroup
     * @return
     */
    BaseResult editGroup(EditGroup editGroup);

    /**
     * 群信息与成员列表查询
     * @param tids
     * @param ope
     * @return
     */
    BaseResult getGrouoUser(String tids,int ope);

    /**
     * 移交群主
     * @param tid
     * @param owner
     * @param newowner
     * @param leave
     * @return
     */
    BaseResult changeOwner(String tid,String owner,String newowner,int leave);

    /**
     * 增加管理员
     * @param tid
     * @param owner
     * @param members
     * @return
     */
    BaseResult addManager(String tid,String owner,String members);

    /**
     * 移除管理员
     * @param tid
     * @param owner
     * @param members
     * @return
     */
    BaseResult removeManager(String tid,String owner,String members);

    /**
     * 获取用户加入群信息
     * @param accid
     * @return
     */
    BaseResult getGroups(String accid);

    /**
     * 修改群昵称
     * @param tid
     * @param owner
     * @param accid
     * @param nick
     * @return
     */
    BaseResult updGroupNickname(String tid,String owner,String accid,String nick);

    /**
     * 修改消息提醒开关
     * @param tid
     * @param accid
     * @param ope
     * @return
     */
    BaseResult changeGroupNotify(String tid,String accid,int ope);

    /**
     * 禁言群成员
     * @param tid
     * @param owner
     * @param accid
     * @param mute
     * @return
     */
    BaseResult muteGroupUser(String tid,String owner,String accid,int mute);

    /**
     * 主动退群
     * @param tid
     * @param accid
     * @return
     */
    BaseResult leave(String tid,String accid);

    /**
     * 全体禁言
     * @param tid
     * @param owner
     * @param mute
     * @return
     */
    BaseResult muteAll(String tid,String owner,String mute);

    /**
     * 获取禁言用户
     * @param tid
     * @param owner
     * @return
     */
    BaseResult getMuteUser(String tid,String owner);
}
