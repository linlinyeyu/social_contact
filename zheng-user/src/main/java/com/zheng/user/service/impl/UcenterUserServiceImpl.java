package com.zheng.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.common.constants.CacheConstants;
import com.zheng.common.util.*;
import com.zheng.im.base.ChatBase;
import com.zheng.im.base.UserBase;
import com.zheng.im.domain.user.User;
import com.zheng.neo4j.dao.domain.Neo4jUser;
import com.zheng.neo4j.rpc.api.Neo4jUserService;
import com.zheng.oss.upload.Upload;
import com.zheng.user.dao.mapper.UcenterUserMapper;
import com.zheng.user.dao.mapper.UcenterUserRelationMapper;
import com.zheng.user.dao.model.UcenterUser;
import com.zheng.user.dao.model.UcenterUserLog;
import com.zheng.user.dao.model.UcenterUserRelation;
import com.zheng.user.model.UcenterUserVO;
import com.zheng.user.service.UcenterUserLogService;
import com.zheng.user.service.UcenterUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* UcenterUserService实现
* Created by shuzheng on 2017/12/14.
*/
@Service
@Transactional
@BaseService
public class UcenterUserServiceImpl extends BaseServiceImplPinet<UcenterUserMapper, UcenterUser> implements UcenterUserService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUserServiceImpl.class);


    @Autowired
    private UcenterUserMapper ucenterUserMapper;

    @Autowired
    private UcenterUserLogService ucenterUserLogService;

    @Autowired
    private Neo4jUserService neo4jUserService;

    @Autowired
    private UcenterUserRelationMapper ucenterUserRelationMapper;

    @Autowired
    private ChatBase chatBase;

    public synchronized BaseResult regist(UcenterUser ucenterUser){
        if(!ValidatorUtil.validPhone(ucenterUser.getPhone())){
            return FormatResponseUtil.error(ErrorCodeEnum.PHONE_NOT_MATCH);
        }
        UcenterUser user1 = ucenterUserMapper.selectByPhone(ucenterUser.getPhone());
        if(user1 != null){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_PHONE_DUPLICATE);
        }
        //注册云信
        String uuid;
        while (true){
            uuid = GenerateUtil.getUuid();
            int count = ucenterUserMapper.selectByUuid(uuid);
            if (count > 0){
                continue;
            }
            break;
        }

        int i = 0;
        UserBase imbase = chatBase.getUserBase();
        User user = new User();
        user.setAccid(uuid);
        user.setMobile(ucenterUser.getPhone());
        user.setName(ucenterUser.getNickname());
        user.setIcon(ucenterUser.getAvatar());
        while (i < 3){
            BaseResult map = imbase.imRegister(user);

            if (FormatResponseUtil.isSuccess(map)){
                JSONObject jsonObject = (JSONObject) map.getData();
                Map<String,Object> info = JSON.parseObject(jsonObject.get("info").toString());
                //更新返回token
                ucenterUser.setYxToken(info.get("token").toString());
                break;
            }else {
                i++;
                continue;
            }
        }

        if (i > 2){
            return FormatResponseUtil.error(ErrorCodeEnum.REGIST_FAIL);
        }

        ucenterUser.setUuid(uuid);
        ucenterUser.setCreateTime(new Date().getTime());
        ucenterUserMapper.insertSelective(ucenterUser);

        Neo4jUser neo4jUser = new Neo4jUser();
        neo4jUser.setActive((short)1);
        neo4jUser.setAvater(ucenterUser.getAvatar());
        neo4jUser.setMobile(ucenterUser.getPhone());
        neo4jUser.setName(ucenterUser.getNickname());
        neo4jUser.setPassword(ucenterUser.getPassword());
        neo4jUser.setUuid(ucenterUser.getUuid());
        neo4jUser.setToken(ucenterUser.getYxToken());
        neo4jUser.setCreateTime(ucenterUser.getCreateTime());
        neo4jUserService.register(neo4jUser);

        return new BaseResult(0,"注册成功", ucenterUser.getUserId());
    }

    @Override
    public String selectName(String uuid) {
        return ucenterUserMapper.selectName(uuid);
    }



    public BaseResult loginPasswd(String mobile, String password){
        if(!ValidatorUtil.validPhone(mobile)){
            return FormatResponseUtil.error(ErrorCodeEnum.PHONE_NOT_MATCH);
        }
        if(StringUtil.isEmpty(password)){
            return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
        }

        UcenterUser user = ucenterUserMapper.selectByPhone(mobile);

        if(user == null){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_OR_PASSWD_ERROR);
        }
        if(user.getActive() == 0){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_NOT_ACTIVE);
        }

        if(!password.equals(user.getPassword())){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_OR_PASSWD_ERROR);
        }

        return FormatResponseUtil.formatResponse();
    }

    @Override
    @Transactional
    public Map<String, Object> login(int userId, String ip, String deviceId, String agent, int terminal,
                                     String jpushToken){

        String content = "用户于" + TimeUtil.getCurrentTime() + "登录";
        UcenterUserLog ucenterUserLog = new UcenterUserLog();
        ucenterUserLog.setUserId(userId);
        ucenterUserLog.setCreateTime(new Date().getTime());
        ucenterUserLog.setIp(ip);
        ucenterUserLog.setDeviceId(deviceId);
        ucenterUserLog.setContent(content);
        ucenterUserLog.setAgent(agent);
        ucenterUserLog.setTerminal( terminal);

        UcenterUser update = new UcenterUser();
        update.setUserId(userId);
        update.setLastLoginIp(ip);
        update.setLastLoginTime(new Date().getTime());

        if(!StringUtil.isEmpty(jpushToken)){
            update.setJpushToken(jpushToken);
            update.setTerminal((short)terminal);
        }

        String token = GenerateUtil.getToken();

        int days = 15;
        int expireTime = days * 24 * 60 * 60;

        update.setToken(token);
        update.setTokenExpireTime(TimeUtil.timeMillsChange(System.currentTimeMillis(), Calendar.DAY_OF_MONTH, days));

        this.updateByPrimaryKeySelective(update);

        ucenterUserLogService.insertSelective(ucenterUserLog);

        Map<String, Object> map = selectAppUserDetail(userId);

        if(map != null && "0".equals(map.get("active").toString())){
            return null;
        }
        return map;
    }



    @Override
    public List<UcenterUser> selectUsersByUuid(List<String> uuids){
        return ucenterUserMapper.selectUsersByUuid(uuids, 3);
    }

    @Override
    public Map<String, UcenterUser> selectUsersByUuidWrappedMap(List<String> uuids, int simpleLevel){
        List<UcenterUser> list = ucenterUserMapper.selectUsersByUuid(uuids, simpleLevel);
        Map<String, UcenterUser> map = WowCollections.map();
        for (UcenterUser user : list){
            user.setAvatar(Upload.getImageUrl(user.getAvatar()));
            map.put(user.getUuid(), user);
        }
        return map;
    }

    @Override
    public Map<String, UcenterUserVO> selectFriendsByUuidWrappedMap(String uuid, List<String> uuids, int simpleLevel){
        List<UcenterUserVO> list = ucenterUserMapper.selectFriendsByUuid(uuid, uuids, simpleLevel);
        Map<String, UcenterUserVO> map = WowCollections.map();
        for (UcenterUserVO user : list){
            user.setAvatar(Upload.getImageUrl(user.getAvatar()));
            map.put(user.getUuid(), user);
        }
        return map;
    }

    @Override
    public UcenterUser selectById(Integer userId){
        return ucenterUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public UcenterUser selectByUuid(String uuid){
        List<String> uuids = WowCollections.list(uuid);
        List<UcenterUser> users = ucenterUserMapper.selectUsersByUuid(uuids, 3);
        return users == null || users.size() == 0 ? null : users.get(0);
    }

    @Override
    public UcenterUser selectByPhone(String phone){
        return ucenterUserMapper.selectByPhone(phone);
    }

    @Override
    public UcenterUser selectByToken(String token){
        return ucenterUserMapper.selectByToken(token);
    }

    @Override
    public Map<String, Object> selectAppUserDetail(Integer userId){

        Map<String, Object> user =  ucenterUserMapper.selectAppDetail(userId);

        if(user != null){
            user.put("avatar", Upload.getImageUrl(user.get("avatar")));
            String qrcode = AESUtil.AESEncode(user.get("uuid").toString());
            user.put("qrcode", RequestUtil.getDomain() + qrcode);
        }

        return user;
    }

    @Override
    public int update(UcenterUser ucenterUser){
        return this.updateByPrimaryKeySelective(ucenterUser);
    }

    @Override
    public int updateAndClearCache(UcenterUser ucenterUser){
        RedisUtil.remove(CacheConstants.USER + ucenterUser.getUserId());
        return this.updateByPrimaryKeySelective(ucenterUser);
    }

    @Override
    public BaseResult selectFriend(String mobile,String uuid) {
        UcenterUser ucenterUser = ucenterUserMapper.selectByPhone(mobile);
        if (ucenterUser == null){
            Map<String,Object> response = new HashMap<String,Object>(){{put("type",0);}};
            return FormatResponseUtil.formatResponse(response);
        }

        List<UcenterUserRelation> ucenterUserRelation = ucenterUserRelationMapper.selectByMultiId(ucenterUser.getUuid(),uuid);
        Map<String,Object> response = new HashMap<String,Object>(){{
            put("uuid",ucenterUser.getUuid());
            put("avater",ucenterUser.getAvatar());
            put("nickname",ucenterUser.getNickname());
            put("phone",ucenterUser.getPhone());
            put("area",ucenterUser.getArea());
        }};

        if (ucenterUserRelation.size() == 0){
            response.put("type",1);
        }else if (ucenterUserRelation.size() == 1){
            response.put("type",2);
        }else if (ucenterUserRelation.size() == 2){
            response.put("type",3);
            for (UcenterUserRelation relation:ucenterUserRelation){
                if (relation.getFriendUuid() == ucenterUser.getUuid()){
                    response.put("alias",relation.getAlias());
                }
            }
        }
        response.put("is_identity",ucenterUser.getIsIdentity());
        return FormatResponseUtil.formatResponse(response);
    }

    @Override
    public BaseResult matchMailList(String uuid, String phones) {
        List<String> phoneList = JSON.parseArray(phones,String.class);
        List<Map<String,Object>> users = ucenterUserMapper.selectByPhoneList(phoneList,uuid);
        return FormatResponseUtil.formatResponse(users);
    }
}