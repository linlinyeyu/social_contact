package com.zheng.user.service;

import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServicePinet;
import com.zheng.user.dao.model.UcenterGroupRelation;

/**
* UcenterGroupRelationService接口
* Created by pinet on 2017/12/19.
*/
public interface UcenterGroupRelationService extends BaseServicePinet<UcenterGroupRelation> {
    /**
     * 踢除群成员
     * @param tid
     * @param uuid
     * @param members
     * @return
     */
    BaseResult kickMembers(String tid, String uuid, String members);

    /**
     * 修改群昵称
     * @param tid
     * @param owner
     * @param accid
     * @param nick
     * @return
     */
    BaseResult changeMemberName(String tid,String owner,String accid,String nick);

    /**
     * 主动退群
     * @param uuid
     * @param tid
     * @return
     */
    BaseResult leaveGroup(String uuid,String tid);

    /**
     *设置管理员
     * @param tid
     * @param uuid
     * @param members
     * @return
     */
    BaseResult setManager(String tid,String uuid,String members);

    /**
     * 移除管理员
     * @param tid
     * @param uuid
     * @param members
     * @return
     */
    BaseResult delManager(String tid,String uuid,String members);

    /**
     * 设置置顶聊天
     * @param tid
     * @param uuid
     * @return
     */
    BaseResult setTop(String tid,String uuid,byte isTop);

    /**
     * 设置是否保存通讯录
     * @param tid
     * @param uuid
     * @param isMail
     * @return
     */
    BaseResult setMail(String tid,String uuid,byte isMail);

    /**
     * 设置消息免打扰
     * @param tid
     * @param uuid
     * @param isOpen
     * @return
     */
    BaseResult setMuteTeam(String tid,String uuid,byte isOpen);
}