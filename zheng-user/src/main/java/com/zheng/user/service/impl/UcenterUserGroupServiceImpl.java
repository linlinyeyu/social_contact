package com.zheng.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.common.util.ErrorCodeEnum;
import com.zheng.common.util.FormatResponseUtil;
import com.zheng.common.util.GeoHash;
import com.zheng.im.base.ChatBase;
import com.zheng.im.base.GroupBase;
import com.zheng.im.base.MessageBase;
import com.zheng.im.domain.group.AddUserGroup;
import com.zheng.im.domain.group.CreateGroup;
import com.zheng.im.domain.group.EditGroup;
import com.zheng.im.domain.message.CustomMessage;
import com.zheng.message.dao.mapper.MessageMapper;
import com.zheng.message.dao.mapper.UcenterUserMessageMapper;
import com.zheng.message.dao.model.Message;
import com.zheng.message.dao.model.UcenterUserMessage;
import com.zheng.neo4j.dao.domain.Neo4jGroup;
import com.zheng.neo4j.rpc.api.Neo4jGroupService;
import com.zheng.user.dao.mapper.*;
import com.zheng.user.dao.model.*;
import com.zheng.user.service.UcenterUserGroupService;
import com.zheng.user.service.UcenterUserRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* UcenterUserGroupService实现
* Created by shuzheng on 2017/12/19.
*/
@Service
@Transactional
@BaseService
public class UcenterUserGroupServiceImpl extends BaseServiceImplPinet<UcenterUserGroupMapper, UcenterUserGroup> implements UcenterUserGroupService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUserGroupServiceImpl.class);

    @Autowired
    private UcenterUserGroupMapper ucenterUserGroupMapper;
    @Autowired
    private ChatBase chatBase;
    @Autowired
    private UcenterGroupRelationMapper ucenterGroupRelationMapper;
    @Autowired
    private Neo4jGroupService neo4jGroupService;
    @Autowired
    private UcenterUserMapper ucenterUserMapper;
    @Autowired
    private UcenterGroupTempMapper groupTempMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UcenterUserMessageMapper ucenterUserMessageMapper;
    @Override
    public BaseResult createGroup(String uuid, String rmembers,String nickname) {
        List<Map> pmember = JSON.parseArray(rmembers,Map.class);
        if(pmember.size() > 200){
            return FormatResponseUtil.error(ErrorCodeEnum.MEMBER_EXCEED);
        }
        List<String> yxmem = new ArrayList<>();
        for (Map member:pmember){
            yxmem.add(String.valueOf(member.get("uuid")) );
        }
        String members = JSON.toJSONString(yxmem);
        GroupBase groupBase = chatBase.getGroupBase();
        CreateGroup group = new CreateGroup();
        group.setTname("群聊");
        group.setOwner(uuid);
        group.setMembers(members);
        group.setMsg(nickname+"邀请您加入群聊");
        group.setMagree(0);
        group.setJoinmode(1);
        BaseResult baseResult = groupBase.createGroup(group);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        //建群
        JSONObject result = (JSONObject) baseResult.getData();
        String yxId = (String)result.get("tid");
        UcenterUserGroup userGroup = new UcenterUserGroup();
        userGroup.setGroupName("群聊");
        userGroup.setOwner(uuid);
        userGroup.setYxTid(yxId);
        userGroup.setDateAdd(new Date().getTime());
        int count = ucenterUserGroupMapper.insertSelective(userGroup);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.CREATE_GROUP_FAIL);
        }
        //插入群成员
        List<UcenterGroupRelation> users = new ArrayList<>();
        UcenterGroupRelation groupRelation = new UcenterGroupRelation();
        groupRelation.setGroupYxId(yxId);
        groupRelation.setIsMember((byte)1);
        groupRelation.setIsOwner((byte)1);
        groupRelation.setUuid(uuid);
        groupRelation.setAlias(nickname);
        users.add(groupRelation);
        boolean flag = insertGroupUser(yxId,pmember,users);
        if (!flag){
            return FormatResponseUtil.error(ErrorCodeEnum.CREATE_GROUP_FAIL);
        }
        //插入图数据库
        pmember.add(new HashMap(){{put("uuid",uuid);}});
        Neo4jGroup neo4jGroup = new Neo4jGroup();
        neo4jGroup.setUuid(uuid);
        neo4jGroup.setTid(yxId);
        List<String> mem = new ArrayList<>();
        for (Map map:pmember){
            mem.add(String.valueOf(map.get("uuid")));
        }
        neo4jGroup.setMembers(mem);
        flag = neo4jGroupService.createGroup(neo4jGroup);
        if (!flag){
            return FormatResponseUtil.error(ErrorCodeEnum.CREATE_GROUP_FAIL);
        }

        //是否满足家族群
        flag = isFamily(yxId);
        if (flag){
            userGroup.setIsRemind((byte)1);
            ucenterUserGroupMapper.updateByUuidAndYxid(userGroup);
            Map<String,Object> attach = new HashMap<String,Object>(){{put("msg","新建群可升级为家族群");}};
            sendMessage(uuid,uuid,attach,(short) 1);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult addMembers(String uuid, String tid, String rmembers,String nickname) {
        List<Map> pmember = JSON.parseArray(rmembers,Map.class);
        List<String> yxmem = new ArrayList<>();
        for (Map member:pmember){
            yxmem.add(String.valueOf(member.get("uuid")));
        }
        String members = JSON.toJSONString(yxmem);
        AddUserGroup userGroup = new AddUserGroup();
        userGroup.setTid(tid);
        userGroup.setOwner(uuid);
        userGroup.setMagree(0);
        userGroup.setMembers(members);
        userGroup.setMsg(nickname+"邀请您加入群聊");
        GroupBase groupBase = chatBase.getGroupBase();
        BaseResult baseResult = groupBase.addUserGroup(userGroup);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        List<UcenterGroupRelation> users = new ArrayList<>();
        boolean flag = insertGroupUser(tid,pmember,users);
        if (!flag){
            return FormatResponseUtil.error(ErrorCodeEnum.ADD_GROUP_USER_FAIL);
        }

        Neo4jGroup  neo4jGroup = new Neo4jGroup();
        neo4jGroup.setUuid(uuid);
        neo4jGroup.setTid(tid);
        List<String> mem = new ArrayList<>();
        for (Map map:pmember){
            mem.add(String.valueOf(map.get("uuid")));
        }
        neo4jGroup.setMembers(mem);
        flag = neo4jGroupService.addGroupRelation(neo4jGroup);
        if (!flag){
            return FormatResponseUtil.error(ErrorCodeEnum.ADD_GROUP_USER_FAIL);
        }
        //是否满足家族群
        UcenterUserGroup ucenterUserGroup = ucenterUserGroupMapper.selectByTid(tid);
        if (ucenterUserGroup.getIsRemind() == 0){
            flag = isFamily(tid);
            if (flag){
                ucenterUserGroup.setIsRemind((byte)1);
                ucenterUserGroupMapper.updateByUuidAndYxid(ucenterUserGroup);
                Map<String,Object> attach = new HashMap<String,Object>(){{put("msg",ucenterUserGroup.getGroupName()+"可升级为家族群");}};
                sendMessage(uuid,ucenterUserGroup.getOwner(),attach,(short)1);
            }
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult updGroup(UcenterUserGroup ucenterUserGroup) {
        GroupBase groupBase = chatBase.getGroupBase();
        EditGroup editGroup = new EditGroup();
        editGroup.setAnnouncement(ucenterUserGroup.getAnnouncement());
        editGroup.setTid(ucenterUserGroup.getYxTid());
        editGroup.setOwner(ucenterUserGroup.getOwner());
        editGroup.setTname(ucenterUserGroup.getGroupName());
        BaseResult baseResult = groupBase.editGroup(editGroup);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        int count = ucenterUserGroupMapper.updateByUuidAndYxid(ucenterUserGroup);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_GROUP_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult removeGroup(String uuid, String tid) {
        GroupBase groupBase = chatBase.getGroupBase();
        BaseResult baseResult = groupBase.remove(tid,uuid);
        if (baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        int count = ucenterUserGroupMapper.deleteByYxTid(tid);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.REMOVE_GROUP_FAIL);
        }
        count = ucenterGroupRelationMapper.deleteByTid(tid);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.REMOVE_GROUP_FAIL);
        }

        boolean result = neo4jGroupService.removeGroup(tid);
        if (!result){
            return FormatResponseUtil.error(ErrorCodeEnum.REMOVE_GROUP_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult modifyGroupLeader(String uuid, String tid, String member) {
        //云信移交
        GroupBase groupBase = chatBase.getGroupBase();
        BaseResult baseResult = groupBase.changeOwner(tid,uuid,member,2);
        if(baseResult.getCode() != 0){
            return FormatResponseUtil.formatResponse(baseResult);
        }

        int count = ucenterUserGroupMapper.updateOwner(uuid,tid,member);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_GROUP_LEADER_FAIL);
        }
        count = ucenterGroupRelationMapper.updateOwner(uuid,tid,member);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.UPD_GROUP_LEADER_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public BaseResult getGroupQr(String tid,String uuid) {
        Map<String,Object> info = ucenterUserGroupMapper.getGroupQr(tid);
        UcenterUser user = ucenterUserMapper.selectBeanByUuid(uuid);
        info.put("nickname",user.getNickname());
        return FormatResponseUtil.formatResponse(info);
    }

    @Override
    public BaseResult getAroundGroup(String uuid,short password, double lat, double lng) {
        GeoHash geoHash = new GeoHash(lat,lng);
        String geo = geoHash.getGeoHashBase32();
        String ngeo = geo.substring(0,7);
        UcenterGroupTemp groupTemp = new UcenterGroupTemp();
        groupTemp.setPassword(password);
        groupTemp.setGeohash(ngeo);
        //搜索是否附近有人
        List<Map<String,Object>> groupTemps = groupTempMapper.selectByPasswordAndGeo(groupTemp);
        UcenterUser ucenterUser = ucenterUserMapper.selectBeanByUuid(uuid);
        groupTemp.setUuid(uuid);
        //判断是否在群中
        int exist = groupTempMapper.selectUserByPg(groupTemp);
        if (exist > 0){
            return FormatResponseUtil.formatResponse(groupTemps);
        }
        groupTemp.setDateAdd(new Date().getTime());
        groupTemp.setGeohash(geo);
        int count = groupTempMapper.insertSelective(groupTemp);
        if (count > 0){
            groupTemps.add(new HashMap<String,Object>(){{put("nickname",ucenterUser.getNickname());put("avatar",ucenterUser.getAvatar());}});
        }
        return FormatResponseUtil.formatResponse(groupTemps);
    }

    @Override
    public BaseResult quitAroundGroup(String uuid, short password, double lat, double lng) {
        UcenterGroupTemp groupTemp = new UcenterGroupTemp();
        GeoHash geoHash = new GeoHash(lat,lng);
        String geo = geoHash.getGeoHashBase32();
        geo = geo.substring(0,7);
        groupTemp.setGeohash(geo);
        groupTemp.setUuid(uuid);
        groupTemp.setPassword(password);
        int exist = groupTempMapper.selectUserByPg(groupTemp);
        if (exist > 0){
            return FormatResponseUtil.formatResponse();
        }
        int count = groupTempMapper.deleteByPug(groupTemp);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.QUIT_AROUND_GROUP_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    @Override
    public synchronized BaseResult joinAroundGroup(String uuid, short password, double lat, double lng,String nickname) {
        GroupBase groupBase = chatBase.getGroupBase();
        //判断群是否存在
        GeoHash geoHash = new GeoHash(lat,lng);
        String geo = geoHash.getGeoHashBase32();
        String ngeo = geo.substring(0,7);
        UcenterUserGroup userGroup = ucenterUserGroupMapper.selectByPg(password,ngeo);
        if (userGroup != null){
            //云信加入
            AddUserGroup addUserGroup = new AddUserGroup();
            addUserGroup.setTid(userGroup.getYxTid());
            addUserGroup.setOwner(userGroup.getOwner());
            addUserGroup.setMagree(0);
            addUserGroup.setMembers(JSON.toJSONString(new String[]{uuid}));
            addUserGroup.setMsg("您通过密码"+password+"加入群聊");
            BaseResult baseResult = groupBase.addUserGroup(addUserGroup);
            if (baseResult.getCode() != 0){
                return FormatResponseUtil.formatResponse(baseResult);
            }
            //本地加入
            List<UcenterGroupRelation> users = new ArrayList<>();
            Map<String,String> mem = new HashMap<String,String>(){{put("uuid",uuid);put("alias",nickname);}};
            List<Map> mems = new ArrayList<Map>(){{add(mem);}};
            boolean flag = insertGroupUser(userGroup.getYxTid(),mems,users);
            if (!flag){
                return FormatResponseUtil.error(ErrorCodeEnum.ADD_GROUP_USER_FAIL);
            }

            //图数据库加入
            Neo4jGroup  neo4jGroup = new Neo4jGroup();
            neo4jGroup.setUuid(userGroup.getOwner());
            neo4jGroup.setTid(userGroup.getYxTid());
            List<String> nmems = new ArrayList<String>(){{add(uuid);}};
            neo4jGroup.setMembers(nmems);
            flag = neo4jGroupService.addGroupRelation(neo4jGroup);
            if (!flag){
                return FormatResponseUtil.error(ErrorCodeEnum.ADD_GROUP_USER_FAIL);
            }
        }else {
            //云信建群
            CreateGroup group = new CreateGroup();
            group.setOwner(uuid);
            group.setTname("群聊");
            group.setMsg("通过密码"+password+"创建群聊");
            group.setMagree(0);
            group.setJoinmode(0);
            group.setMembers(JSON.toJSONString(new String[]{uuid}));
            BaseResult baseResult = groupBase.createGroup(group);
            if (baseResult.getCode() < 0){
                return baseResult;
            }
            //本地建群
            Map<String,Object> result = (JSONObject)baseResult.getData();
            String tid = (String)result.get("tid");
            UcenterUserGroup group1 = new UcenterUserGroup();
            group1.setOwner(uuid);
            group1.setGroupName("群聊");
            group1.setGeohash(geo);
            group1.setPassword(password);
            group1.setYxTid(tid);
            group1.setDateAdd(new Date().getTime());
            int count = ucenterUserGroupMapper.insertSelective(group1);
            if (count < 1){
                return FormatResponseUtil.error(ErrorCodeEnum.CREATE_GROUP_FAIL);
            }

            //插入成员
            List<UcenterGroupRelation> users = new ArrayList<>();
            Map<String,String> mem = new HashMap<String,String>(){{put("uuid",uuid);put("alias",nickname);}};
            List<Map> mems = new ArrayList<Map>(){{add(mem);}};
            boolean flag = insertGroupUser(tid,mems,users);
            if (!flag){
                return FormatResponseUtil.error(ErrorCodeEnum.ADD_GROUP_USER_FAIL);
            }
            //插入图数据库
            Neo4jGroup neo4jGroup = new Neo4jGroup();
            neo4jGroup.setTid(tid);
            neo4jGroup.setUuid(uuid);
            List<String> members = new ArrayList<String>(){{add(uuid);}};
            neo4jGroup.setMembers(members);
            flag = neo4jGroupService.createGroup(neo4jGroup);
            if (!flag){
                return FormatResponseUtil.error(ErrorCodeEnum.CREATE_GROUP_FAIL);
            }
        }

        UcenterGroupTemp groupTemp = new UcenterGroupTemp();
        groupTemp.setPassword(password);
        groupTemp.setUuid(uuid);
        groupTemp.setGeohash(ngeo);
        int count = groupTempMapper.deleteByPug(groupTemp);
        if (count < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.QUIT_AROUND_GROUP_FAIL);
        }
        return FormatResponseUtil.formatResponse();
    }

    private boolean insertGroupUser(String yxId, List<Map> requestMem, List<UcenterGroupRelation> users){
        for (Map member:requestMem){
            UcenterGroupRelation groupRelation = new UcenterGroupRelation();
            groupRelation.setIsOwner((byte)0);
            groupRelation.setIsMember((byte)0);
            groupRelation.setUuid(String.valueOf(member.get("uuid")));
            groupRelation.setAlias((String)member.get("alias"));
            groupRelation.setGroupYxId(yxId);
            users.add(groupRelation);
        }

        int count = ucenterGroupRelationMapper.insertBatch(users);
        if (count < 1){
            return false;
        }
        return true;
    }

    private boolean isFamily(String tid){
        boolean flag = neo4jGroupService.isFamily(tid);
        return flag;
    }

    public boolean sendMessage(String uuid,String fuuid,Map<String,Object> message,short messageType){
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
            int count = messageMapper.insertSelective(message1);
            if (count > 0){
                UcenterUserMessage userMessage = new UcenterUserMessage();
                userMessage.setMessageId(message1.getMessageId());
                userMessage.setCreateTime(new Date().getTime());
                userMessage.setUuid(fuuid);
                ucenterUserMessageMapper.insertSelective(userMessage);
            }
        }

        return true;
    }
}