package com.zheng.user.service;

import com.sun.xml.internal.rngom.parse.host.Base;
import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServicePinet;
import com.zheng.user.dao.model.UcenterUserRelation;

import java.util.Map;
import java.util.List;

/**
* UcenterUserRelationService接口
* Created by pinet on 2017/12/14.
*/
public interface UcenterUserRelationService extends BaseServicePinet<UcenterUserRelation> {
    /**
     * 申请加好友
     * @param uuid
     * @param fuuid
     * @param msg
     * @param alias
     * @param relationType
     * @return
     */
    BaseResult addFriend(String uuid, String fuuid, String msg, String alias, byte relationType);

    /**
     * 获取用户好友列表，relationType为0时表示全部
     * */
    List<Map<String, Object>> selectUserFriends(String uuid, short... relationType);

    /**
     * 确认加好友
     * @param uuid
     * @param fuuid
     * @param alias
     * @return
     */
    BaseResult confirmFriend(String uuid,String fuuid,String alias);

    /**
     * 删除好友
     * @param uuid
     * @param fuuid
     * @return
     */
    BaseResult delFriend(String uuid,String fuuid);

    /**
     * 拒绝好友
     * @param uuid
     * @param fuuid
     * @return
     */
    BaseResult rejectFriend(String uuid,String fuuid);

    /**
     * 获取申请好友分组信息
     * @param uuid
     * @param fuuid
     * @return
     */
    BaseResult applyGroup(String uuid,String fuuid);

    /**
     * 修改备注名
     * @param uuid
     * @param fuuid
     * @param alias
     * @return
     */
    BaseResult modifyAlias(String uuid,String fuuid,String alias);

    /**
     * 修改分组
     * @param uuid
     * @param fuuid
     * @param relationType
     * @return
     */
    BaseResult modifyRelation(String uuid,String fuuid,byte relationType);

    /**
     * 发送修改消息
     * @param uuid
     * @param fuuid
     * @param relationType
     * @return
     */
    BaseResult sendModifyRelationMessage(String uuid,String fuuid,byte relationType);

    /**
     * 拒绝修改分组
     * @param uuid
     * @param fuuid
     * @param relationType
     * @return
     */
    BaseResult rejectModifyRelation(String uuid,String fuuid,byte relationType);
}