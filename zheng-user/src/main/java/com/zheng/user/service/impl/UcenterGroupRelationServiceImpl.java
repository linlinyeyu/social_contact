package com.zheng.user.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.common.util.ErrorCodeEnum;
import com.zheng.common.util.FormatResponseUtil;
import com.zheng.im.base.ChatBase;
import com.zheng.im.base.GroupBase;
import com.zheng.neo4j.rpc.api.Neo4jGroupService;
import com.zheng.user.dao.mapper.UcenterGroupRelationMapper;
import com.zheng.user.dao.mapper.UcenterUserGroupMapper;
import com.zheng.user.dao.model.UcenterGroupRelation;
import com.zheng.user.dao.model.UcenterUserGroup;
import com.zheng.user.service.UcenterGroupRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterGroupRelationService实现
* Created by shuzheng on 2017/12/19.
*/
@Service
@Transactional
@BaseService
public class UcenterGroupRelationServiceImpl extends BaseServiceImplPinet<UcenterGroupRelationMapper, UcenterGroupRelation> implements UcenterGroupRelationService {

    private static Logger _log = LoggerFactory.getLogger(UcenterGroupRelationServiceImpl.class);

    @Autowired
    private UcenterGroupRelationMapper ucenterGroupRelationMapper;

    @Autowired
    private Neo4jGroupService neo4jGroupService;

    @Autowired
    private ChatBase chatBase;

    @Autowired
    private UcenterUserGroupMapper ucenterUserGroupMapper;

    @Override
    public BaseResult kickMembers(String tid, String uuid, String members) {
        GroupBase groupBase = chatBase.getGroupBase();
        BaseResult baseResult = groupBase.kickUser(tid,uuid,members);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        int count = ucenterGroupRelationMapper.deleteByTidAndUuid(tid,members);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.KICK_USER_FAIL);
        }

        boolean flag = neo4jGroupService.deleteGroupRelation(tid,uuid,members);
        if (!flag){
            return FormatResponseUtil.error(ErrorCodeEnum.KICK_USER_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult changeMemberName(String tid, String owner, String accid, String nick) {
        GroupBase groupBase = chatBase.getGroupBase();
        BaseResult baseResult = groupBase.updGroupNickname(tid,owner,accid,nick);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        UcenterGroupRelation groupRelation = new UcenterGroupRelation();
        groupRelation.setGroupYxId(tid);
        groupRelation.setUuid(accid);
        groupRelation.setAlias(nick);
        int count = ucenterGroupRelationMapper.updateByTidAndUuid(groupRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_GROUP_ALIAS_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult leaveGroup(String uuid, String tid) {
        UcenterGroupRelation groupRelation = ucenterGroupRelationMapper.selectByUuidAndTid(uuid,tid);
        int memberCount = 0;
        if (groupRelation.getIsOwner() == 1){
            memberCount = ucenterGroupRelationMapper.countGroupMember(tid);
            if (memberCount > 1){
                return FormatResponseUtil.error(ErrorCodeEnum.LEADER_NOT_CHANGE);
            }
        }
        GroupBase groupBase = chatBase.getGroupBase();
        BaseResult baseResult = groupBase.leave(tid,uuid);
        if (baseResult.getCode() < 0){
            return baseResult;
        }

        int count = ucenterGroupRelationMapper.deleteByTidAndUuid(tid,uuid);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.LEAVE_GROUP_FAIL);
        }

        UcenterUserGroup userGroup = ucenterUserGroupMapper.selectByTid(tid);
        boolean flag = neo4jGroupService.deleteGroupRelation(tid,userGroup.getOwner(),uuid);
        if (!flag){
            return FormatResponseUtil.error(ErrorCodeEnum.LEAVE_GROUP_FAIL);
        }

        if (memberCount == 1){
            count = ucenterUserGroupMapper.deleteByYxTid(tid);
            if (count < 1){
                return FormatResponseUtil.error(ErrorCodeEnum.REMOVE_GROUP_FAIL);
            }
            flag = neo4jGroupService.removeGroup(tid);
            if (!flag){
                return FormatResponseUtil.error(ErrorCodeEnum.REMOVE_GROUP_FAIL);
            }
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult setManager(String tid, String uuid, String members) {
        GroupBase groupBase = chatBase.getGroupBase();
        BaseResult baseResult = groupBase.addManager(tid,uuid,members);
        if (baseResult.getCode() < 0){
            return baseResult;
        }
        UcenterGroupRelation groupRelation = new UcenterGroupRelation();
        groupRelation.setUuid(uuid);
        groupRelation.setGroupYxId(tid);
        groupRelation.setIsMember((byte)1);
        int count = ucenterGroupRelationMapper.updateByTidAndUuid(groupRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_MANAGER_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult delManager(String tid, String uuid, String members) {
        GroupBase groupBase = chatBase.getGroupBase();
        BaseResult baseResult = groupBase.removeManager(tid,uuid,members);
        if (baseResult.getCode() < 0){
            return baseResult;
        }

        UcenterGroupRelation groupRelation = new UcenterGroupRelation();
        groupRelation.setUuid(uuid);
        groupRelation.setGroupYxId(tid);
        groupRelation.setIsMember((byte)0);
        int count = ucenterGroupRelationMapper.updateByTidAndUuid(groupRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_MANAGER_FAIL);
        }

        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult setTop(String tid, String uuid,byte isTop) {
        UcenterGroupRelation groupRelation = new UcenterGroupRelation();
        groupRelation.setGroupYxId(tid);
        groupRelation.setUuid(uuid);
        groupRelation.setIsTop(isTop);
        int count = ucenterGroupRelationMapper.updateByTidAndUuid(groupRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.SET_TOP_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult setMail(String tid, String uuid, byte isMail) {
        UcenterGroupRelation groupRelation = new UcenterGroupRelation();
        groupRelation.setGroupYxId(tid);
        groupRelation.setUuid(uuid);
        groupRelation.setIsAddMail(isMail);
        int count = ucenterGroupRelationMapper.updateByTidAndUuid(groupRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.SET_MAIL_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult setMuteTeam(String tid, String uuid, byte isOpen) {
        GroupBase groupBase = chatBase.getGroupBase();
        BaseResult baseResult = groupBase.changeGroupNotify(tid,uuid,isOpen);
        if (baseResult.getCode() < 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }
        UcenterGroupRelation groupRelation = new UcenterGroupRelation();
        groupRelation.setGroupYxId(tid);
        groupRelation.setUuid(uuid);
        groupRelation.setIsMuteTeam(isOpen);
        int count = ucenterGroupRelationMapper.updateByTidAndUuid(groupRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.SET_MUTE_TEAM_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }
}