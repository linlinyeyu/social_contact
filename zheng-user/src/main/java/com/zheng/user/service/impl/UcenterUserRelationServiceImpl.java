package com.zheng.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.common.util.ErrorCodeEnum;
import com.zheng.common.util.FormatResponseUtil;
import com.zheng.im.base.ChatBase;
import com.zheng.im.base.MessageBase;
import com.zheng.im.base.UserBase;
import com.zheng.im.domain.message.CustomMessage;
import com.zheng.im.domain.user.Friend;
import com.zheng.message.dao.mapper.MessageMapper;
import com.zheng.message.dao.mapper.UcenterUserMessageMapper;
import com.zheng.message.dao.model.Message;
import com.zheng.message.dao.model.UcenterUserMessage;
import com.zheng.neo4j.dao.domain.Neo4jFriend;
import com.zheng.neo4j.dao.domain.Neo4jGroup;
import com.zheng.neo4j.rpc.api.Neo4jGroupService;
import com.zheng.neo4j.rpc.api.Neo4jUserService;
import com.zheng.user.dao.mapper.*;
import com.zheng.user.dao.model.*;
import com.zheng.user.service.UcenterUserRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* UcenterUserRelationService实现
* Created by shuzheng on 2017/12/14.
*/
@Service
@Transactional
@BaseService
public class UcenterUserRelationServiceImpl extends BaseServiceImplPinet<UcenterUserRelationMapper, UcenterUserRelation> implements UcenterUserRelationService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUserRelationServiceImpl.class);

    @Autowired
    UcenterUserRelationMapper ucenterUserRelationMapper;
    @Autowired
    private ChatBase chatBase;
    @Autowired
    private Neo4jUserService neo4jUserService;

    @Autowired
    private UcenterUserMapper ucenterUserMapper;

    @Autowired
    private UcenterRelationTypeMapper ucenterRelationTypeMapper;
    @Autowired
    private UcenterUserMessageMapper ucenterUserMessageMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private Neo4jGroupService neo4jGroupService;
    @Autowired
    private UcenterUserGroupMapper ucenterUserGroupMapper;

    @Override
    public BaseResult addFriend(String uuid,String fuuid,String msg,String alias,byte relationType) {
        //发送云信请求
        Friend friend = new Friend();
        friend.setAccid(uuid);
        friend.setFaccid(fuuid);
        UcenterUser user = ucenterUserMapper.selectBeanByUuid(uuid);
        Map<String,Object> message = new HashMap<String,Object>(){{put("is_identity",user.getIsIdentity());put("relation_type",relationType);put("message",msg);}};
        friend.setMsg(JSON.toJSONString(message));
        friend.setType(2);
        UserBase userBase = chatBase.getUserBase();
        BaseResult baseResult = userBase.addFriend(friend);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        //记录本地数据库
        UcenterUserRelation ucenterUserRelation = new UcenterUserRelation();
        ucenterUserRelation.setUserUuid(uuid);
        ucenterUserRelation.setFriendUuid(fuuid);
        ucenterUserRelation.setAlias(alias);
        ucenterUserRelation.setRelationType(relationType);
        ucenterUserRelation.setDateAdd(new Date().getTime());
        ucenterUserRelation.setDateUpd(new Date().getTime());
        int count = ucenterUserRelationMapper.insertSelective(ucenterUserRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.ADD_FRIEND_ERROR);
        }

        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult confirmFriend(String uuid, String fuuid, String alias) {
        UserBase userBase = chatBase.getUserBase();
        Friend friend = new Friend();
        friend.setAccid(uuid);
        friend.setFaccid(fuuid);
        friend.setType(3);
        BaseResult baseResult = userBase.addFriend(friend);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }
        //更新确认人昵称
        if (alias != null){
            friend.setAccid(uuid);
            friend.setFaccid(fuuid);
            friend.setAlias(alias);
            baseResult = userBase.updFriend(friend);
            if (baseResult.getCode() != 0){
                return FormatResponseUtil.formatResponse(baseResult);
            }
        }
        //更新请求人昵称
        UcenterUserRelation userRelation = ucenterUserRelationMapper.selectByUuid(fuuid,uuid);
        if (userRelation.getAlias() != null){
            friend.setAccid(fuuid);
            friend.setFaccid(uuid);
            friend.setAlias(userRelation.getAlias());
            baseResult = userBase.updFriend(friend);
            if (baseResult.getCode() != 0){
                return FormatResponseUtil.formatResponse(baseResult);
            }
        }

        //更新请求人关系
        UcenterUserRelation ucenterUserRelation = new UcenterUserRelation();

        //同步云信
        BaseResult result = modifyYxRelation(uuid,fuuid,userRelation.getRelationType());
        if (result.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        ucenterUserRelation.setDateUpd(new Date().getTime());
        ucenterUserRelation.setUserUuid(fuuid);
        ucenterUserRelation.setFriendUuid(uuid);
        ucenterUserRelation.setStatus((short)1);
        int count = ucenterUserRelationMapper.updateByUuid(ucenterUserRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_FRIEND_FAIL);
        }

        //插入确认人关系
        ucenterUserRelation.setUserUuid(uuid);
        ucenterUserRelation.setFriendUuid(fuuid);
        ucenterUserRelation.setRelationType(userRelation.getRelationType());
        ucenterUserRelation.setStatus((short)1);
        ucenterUserRelation.setAlias(alias);
        ucenterUserRelation.setDateAdd(new Date().getTime());
        ucenterUserRelation.setDateUpd(new Date().getTime());
        count = ucenterUserRelationMapper.insertSelective(ucenterUserRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_FRIEND_FAIL);
        }

        //图数据库
        Neo4jFriend neo4jFriend = new Neo4jFriend();
        neo4jFriend.setAccid(fuuid);
        neo4jFriend.setFaccid(uuid);
        neo4jFriend.setRelationType((short)userRelation.getRelationType());
        boolean flag = neo4jUserService.addFriend(neo4jFriend);
        if (!flag){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_FRIEND_FAIL);
        }

        //判断家族群
        if (userRelation.getRelationType() == 1){
            List<String> tids = neo4jGroupService.getGroupbyUser(uuid,fuuid);
            if (tids.size() > 0){
                List<UcenterUserGroup> groups = ucenterUserGroupMapper.selectGroupsByTids(tids);
                for (UcenterUserGroup group:groups){
                    if (group.getIsRemind() == 0){
                        flag = neo4jGroupService.isFamily(group.getYxTid());
                        if (flag){
                            Map<String,Object> map = new HashMap<String, Object>(){{put("message",group.getGroupName()+"可升级为家族群");}};
                            sendMessage(uuid,group.getOwner(),null,map,(short) 1);
                        }
                    }
                }
            }
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult applyGroup(String uuid, String fuuid) {
        UcenterUserRelation ucenterUserRelation = ucenterUserRelationMapper.selectByUuid(fuuid,uuid);
        Map<String,Object> map = new HashMap<String,Object>(){{put("relation_type",ucenterUserRelation.getRelationType());}};
        return FormatResponseUtil.formatResponse(map);
    }

    @Override
    public BaseResult rejectFriend(String uuid, String fuuid) {
        UserBase userBase = chatBase.getUserBase();
        Friend friend = new Friend();
        friend.setAccid(fuuid);
        friend.setFaccid(uuid);
        friend.setType(4);
        BaseResult baseResult = userBase.addFriend(friend);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        int count = ucenterUserRelationMapper.deleteRelationByUuid(fuuid,uuid);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.REJECT_FRIEND_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult delFriend(String uuid, String fuuid) {
        //云信删除好友
        UserBase userBase = chatBase.getUserBase();
        Friend friend = new Friend();
        friend.setAccid(uuid);
        friend.setFaccid(fuuid);
        BaseResult baseResult = userBase.delFriend(friend);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }
        int count = ucenterUserRelationMapper.deleteRelationByUuid(uuid,fuuid);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.DEL_FRIEND_FAIL);
        }

        Neo4jFriend neo4jFriend = new Neo4jFriend();
        neo4jFriend.setAccid(uuid);
        neo4jFriend.setFaccid(fuuid);
        boolean flag = neo4jUserService.delFriend(neo4jFriend);
        if(!flag){
            return FormatResponseUtil.error(ErrorCodeEnum.DEL_FRIEND_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult modifyAlias(String uuid, String fuuid, String alias) {
        UserBase userBase = chatBase.getUserBase();
        Friend friend = new Friend();
        friend.setAccid(uuid);
        friend.setFaccid(fuuid);
        friend.setAlias(alias);
        BaseResult baseResult = userBase.updFriend(friend);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        UcenterUserRelation ucenterUserRelation = new UcenterUserRelation();
        ucenterUserRelation.setUserUuid(uuid);
        ucenterUserRelation.setFriendUuid(fuuid);
        ucenterUserRelation.setAlias(alias);
        int count = ucenterUserRelationMapper.updateByUuid(ucenterUserRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_FRIEND_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult modifyRelation(String uuid, String fuuid, byte relationType) {
        BaseResult baseResult = modifyYxRelation(uuid,fuuid,relationType);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        UcenterUserRelation ucenterUserRelation = ucenterUserRelationMapper.selectByUuid(uuid,fuuid);
        byte oldRelationType = ucenterUserRelation.getRelationType();
        ucenterUserRelation.setUserUuid(uuid);
        ucenterUserRelation.setFriendUuid(fuuid);
        ucenterUserRelation.setRelationType(relationType);
        int count = ucenterUserRelationMapper.updateBySelectUuid(ucenterUserRelation);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_RELATION_FAIL);
        }

        Neo4jFriend neo4jFriend = new Neo4jFriend();
        neo4jFriend.setAccid(uuid);
        neo4jFriend.setFaccid(fuuid);
        neo4jFriend.setRelationType((short)relationType);
        boolean result = neo4jUserService.modifyRelation(neo4jFriend);
        if (!result){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_RELATION_FAIL);
        }

        //发送更改消息
        UcenterUser user = ucenterUserMapper.selectBeanByUuid(uuid);
        Map<String,Object> map = new HashMap<>();
        if (oldRelationType > relationType){
            map.put("message",user.getNickname()+"确认关系修改");
        }else {
            UcenterRelationType type = ucenterRelationTypeMapper.selectByPrimaryKey(relationType);
            map.put("message",user.getNickname()+"将关系修改为"+type.getName());
        }
        sendMessage(uuid,fuuid,relationType,map,(short) 2);
        //判断是否可升级为家族群
        if (relationType == 1){
            List<String> tids = neo4jGroupService.getGroupbyUser(uuid,fuuid);
            if (tids.size() > 0){
                List<UcenterUserGroup> groups = ucenterUserGroupMapper.selectGroupsByTids(tids);
                for (UcenterUserGroup group:groups){
                    if (group.getIsRemind() == 0){
                        boolean flag = neo4jGroupService.isFamily(group.getYxTid());
                        if (flag){
                            Map<String,Object> message = new HashMap<String, Object>(){{put("message",group.getGroupName()+"可升级为家族群");}};
                            sendMessage(uuid,group.getOwner(),null,message,(short) 1);
                        }
                    }
                }
            }
        }
        return FormatResponseUtil.formatResponse();
    }

    private BaseResult modifyYxRelation(String uuid, String fuuid, byte relationType){
        UserBase userBase = chatBase.getUserBase();
        Friend friend = new Friend();
        //同步云信
        friend.setAccid(uuid);
        friend.setFaccid(fuuid);
        String relation = JSON.toJSONString(new HashMap<String,Object>(){{put("relation_type",relationType);}});
        friend.setEx(relation);
        BaseResult baseResult = userBase.updFriend(friend);
        if (baseResult.getCode() != 0){
            return baseResult;
        }
        friend.setAccid(fuuid);
        friend.setFaccid(uuid);
        friend.setEx(relation);
        baseResult = userBase.updFriend(friend);
        return baseResult;
    }

    @Override
    public BaseResult sendModifyRelationMessage(String uuid, String fuuid, byte relationType) {
        UcenterUser user = ucenterUserMapper.selectBeanByUuid(uuid);
        UcenterRelationType type = ucenterRelationTypeMapper.selectByPrimaryKey(relationType);
        Map<String,Object> message = new HashMap<String, Object>(){{put("message",user.getNickname()+"请求修改关系为"+type.getName());put("relation_type",relationType);}};
        sendMessage(uuid,fuuid,relationType,message,(short) 2);
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult rejectModifyRelation(String uuid, String fuuid, byte relationType) {
        UcenterUser user = ucenterUserMapper.selectBeanByUuid(uuid);
        Map<String,Object> message = new HashMap<String, Object>(){{put("message",user.getNickname()+"拒绝关系修改");}};
        boolean flag = sendMessage(uuid,fuuid,relationType,message,(short)2);
        if (!flag){
            return FormatResponseUtil.error(ErrorCodeEnum.SEND_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    public boolean sendMessage(String uuid,String fuuid,Byte relationType,Map<String,Object> message,short messageType){
        MessageBase messageBase = chatBase.getMessageBase();
        CustomMessage customMessage = new CustomMessage();
        customMessage.setFrom(uuid);
        customMessage.setTo(fuuid);
        customMessage.setMsgtype(0);
        customMessage.setAttach(JSON.toJSONString(message));
        BaseResult baseResult = messageBase.sendCustomMsg(customMessage);
        if (baseResult.getCode() == 0){
            Message message1 = new Message();
            message1.setContent((String) message.get("message"));
            message1.setMessageType(messageType);
            message1.setTargetType((short)1);
            message1.setSendTime(new Date().getTime());
            message1.setCreateTime(new Date().getTime());
            message1.setSenderType((short)1);
            message1.setRelationType(relationType);
            int count = messageMapper.insertSelective(message1);
            if (count > 0){
                UcenterUserMessage userMessage = new UcenterUserMessage();
                userMessage.setMessageId(message1.getMessageId());
                userMessage.setCreateTime(new Date().getTime());
                userMessage.setUuid(fuuid);
                ucenterUserMessageMapper.insertSelective(userMessage);
            }else {
                return false;
            }
        }else {
            return false;
        }

        return true;
    }

    @Override
    public List<Map<String, Object>> selectUserFriends(String uuid, short... relationType){

        return ucenterUserRelationMapper.selectUserFriends(uuid, relationType);

    }
}