package com.zheng.api.server.controller;

import com.zheng.api.server.aop.ValidateLoginAspect;
import com.zheng.common.base.BaseApiController;
import com.zheng.common.base.BaseResult;
import com.zheng.user.dao.model.UcenterUserGroup;
import com.zheng.user.service.UcenterGroupRelationService;
import com.zheng.user.service.UcenterUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by linlinyeyu on 2017/12/19.
 */
@RestController
@RequestMapping("/api/userGroup")
public class UserGroupController extends BaseApiController {
    @Autowired
    private UcenterUserGroupService ucenterUserGroupService;
    @Autowired
    private UcenterGroupRelationService ucenterGroupRelationService;
    //创建群
    @ValidateLoginAspect
    @RequestMapping(value = "/createGroup",method = RequestMethod.POST)
    public BaseResult createGroup(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String members = param.get("members");
        String nickname = param.get("nickname");
        return ucenterUserGroupService.createGroup(uuid,members,nickname);
    }
    //添加群成员
    @ValidateLoginAspect
    @RequestMapping(value = "/addMembers",method = RequestMethod.POST)
    public BaseResult addMembers(@RequestBody Map<String,String> param){
        String tid = param.get("tid");
        String members = param.get("members");
        String nickname = param.get("nickname");
        String owner = param.get("owner");
        return ucenterUserGroupService.addMembers(owner,tid,members,nickname);
    }

    //踢除群成员
    @ValidateLoginAspect
    @RequestMapping(value = "/kickMembers",method = RequestMethod.POST)
    public BaseResult kickMembers(@RequestBody Map<String,String> param){
        String owner = param.get("owner");
        String tid = param.get("tid");
        String members = param.get("members");
        return ucenterGroupRelationService.kickMembers(tid,owner,members);
    }

    //编辑群资料
    @ValidateLoginAspect
    @RequestMapping(value = "/editGroup",method = RequestMethod.POST)
    public BaseResult editGroup(@RequestBody Map<String,String> param){
        String tid = param.get("tid");
        String announcement = param.get("announcement");
        String name = param.get("group_name");
        String owner = param.get("owner");
        UcenterUserGroup userGroup = new UcenterUserGroup();
        userGroup.setAnnouncement(announcement);
        userGroup.setOwner(owner);
        userGroup.setYxTid(tid);
        userGroup.setGroupName(name);
        return ucenterUserGroupService.updGroup(userGroup);
    }

    //解散群
    @ValidateLoginAspect
    @RequestMapping(value = "/removeGroup",method = RequestMethod.POST)
    public BaseResult removeGroup(@RequestBody Map<String,String> param){
        String tid = param.get("tid");
        String uuid = getUuid();
        return ucenterUserGroupService.removeGroup(uuid,tid);
    }

    //更换群主
    @ValidateLoginAspect
    @RequestMapping(value = "/changeOwner",method = RequestMethod.POST)
    public BaseResult changeOwner(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String tid = param.get("tid");
        String member = param.get("member");
        return ucenterUserGroupService.modifyGroupLeader(uuid,tid,member);
    }
    //修改群昵称
    @ValidateLoginAspect
    @RequestMapping(value = "/changeMemberNick",method = RequestMethod.POST)
    public BaseResult changeMemberNick(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String tid = param.get("tid");
        String owner = param.get("owner");
        String nick = param.get("nick");
        return ucenterGroupRelationService.changeMemberName(tid,owner,uuid,nick);
    }

    //群二维码
    @ValidateLoginAspect
    @RequestMapping(value = "/getGroupQr",method = RequestMethod.GET)
    public BaseResult getGroupQr(@RequestParam String tid){
        String uuid = getUuid();
        return ucenterUserGroupService.getGroupQr(tid,uuid);
    }

    //主动退群
    @ValidateLoginAspect
    @RequestMapping(value = "/leaveGroup",method = RequestMethod.POST)
    public BaseResult leaveGroup(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String tid = param.get("tid");
        return ucenterGroupRelationService.leaveGroup(uuid,tid);
    }

    //获取面对面群消息
    @ValidateLoginAspect
    @RequestMapping(value = "/getAroundGroup",method = RequestMethod.POST)
    public BaseResult getAroundGroup(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        short password = Short.valueOf(param.get("password"));
        double lat = Double.valueOf(param.get("lat"));
        double lng = Double.valueOf(param.get("lng"));
        return ucenterUserGroupService.getAroundGroup(uuid,password,lat,lng);
    }

    //退出面对面群消息
    @ValidateLoginAspect
    @RequestMapping(value = "/quitAroundGroup",method = RequestMethod.POST)
    public BaseResult quitAroundGroup(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        short password = Short.valueOf(param.get("password"));
        double lat = Double.valueOf(param.get("lat"));
        double lng = Double.valueOf(param.get("lng"));
        return ucenterUserGroupService.quitAroundGroup(uuid,password,lat,lng);
    }

    //加入面对面群聊
    @ValidateLoginAspect
    @RequestMapping(value = "/joinAroundGroup",method = RequestMethod.POST)
    public BaseResult joinAroundGroup(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        short password = Short.valueOf(param.get("password"));
        double lat = Double.valueOf(param.get("lat"));
        double lng = Double.valueOf(param.get("lng"));
        String nickname = param.get("nickname");
        return ucenterUserGroupService.joinAroundGroup(uuid,password,lat,lng,nickname);
    }

    //设置管理员
    @ValidateLoginAspect
    @RequestMapping(value = "/addManager",method = RequestMethod.POST)
    public BaseResult addManager(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String tid = param.get("tid");
        String members = param.get("members");
        return ucenterGroupRelationService.setManager(tid,uuid,members);
    }

    //移除管理员
    @ValidateLoginAspect
    @RequestMapping(value = "/delManager",method = RequestMethod.POST)
    public BaseResult delManager(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String tid = param.get("tid");
        String members = param.get("members");
        return ucenterGroupRelationService.delManager(tid,uuid,members);
    }

    //设置群聊置顶
    @ValidateLoginAspect
    @RequestMapping(value = "/setTop",method = RequestMethod.POST)
    public BaseResult setTop(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String tid = param.get("tid");
        byte isTop = Byte.valueOf(param.get("is_top"));
        return ucenterGroupRelationService.setTop(tid,uuid,isTop);
    }

    //设置是否保存通讯录
    @ValidateLoginAspect
    @RequestMapping(value = "/setMail",method = RequestMethod.POST)
    public BaseResult setMail(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String tid = param.get("tid");
        byte isMail = Byte.valueOf(param.get("is_mail"));
        return ucenterGroupRelationService.setMail(tid,uuid,isMail);
    }

    //设置消息免打扰
    @ValidateLoginAspect
    @RequestMapping(value = "/setMuteTeam",method = RequestMethod.POST)
    public BaseResult setMuteTeam(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String tid = param.get("tid");
        byte isMuteTeam = Byte.valueOf(param.get("is_mute_team"));
        return ucenterGroupRelationService.setMuteTeam(tid,uuid,isMuteTeam);
    }
}
