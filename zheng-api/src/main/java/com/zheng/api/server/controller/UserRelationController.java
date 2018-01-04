package com.zheng.api.server.controller;

import com.zheng.api.server.aop.ValidateLoginAspect;
import com.zheng.common.base.BaseApiController;
import com.zheng.common.base.BaseResult;
import com.zheng.user.service.UcenterUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by linlinyeyu on 2017/12/19.
 */
@RestController
@RequestMapping("/api/userRelation")
public class UserRelationController extends BaseApiController {
    @Autowired
    private UcenterUserRelationService ucenterUserRelationService;

    //添加好友
    @ValidateLoginAspect
    @RequestMapping(value = "/addFriend",method = RequestMethod.POST)
    public BaseResult addFriend(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String fuuid = param.get("fuuid");
        String msg = param.get("msg");
        String alias = param.get("alias");
        byte relationType = Byte.valueOf(param.get("relation_type"));
        return ucenterUserRelationService.addFriend(uuid,fuuid,msg,alias,relationType);
    }

    //申请好友分组消息
    @ValidateLoginAspect
    @RequestMapping(value = "/applyGroup",method = RequestMethod.GET)
    public BaseResult applyGroup(@RequestParam Map<String,String> param){
        return ucenterUserRelationService.applyGroup(getUuid(),param.get("fuuid"));
    }

    //接收添加
    @ValidateLoginAspect
    @RequestMapping(value = "/confirmFriend",method = RequestMethod.POST)
    public BaseResult confirmFriend(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String fuuid = param.get("fuuid");
        String alias = param.get("alias");
        return ucenterUserRelationService.confirmFriend(uuid,fuuid,alias);
    }

    //删除好友
    @ValidateLoginAspect
    @RequestMapping(value = "/delFriend",method = RequestMethod.POST)
    public BaseResult delFriend(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String fuuid = param.get("fuuid");
        return ucenterUserRelationService.delFriend(uuid,fuuid);
    }

    //拒绝添加
    @ValidateLoginAspect
    @RequestMapping(value = "/rejectFriend",method = RequestMethod.POST)
    public BaseResult rejectFriend(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String fuuid = param.get("fuuid");
        return ucenterUserRelationService.rejectFriend(uuid,fuuid);
    }

    //修改备注
    @ValidateLoginAspect
    @RequestMapping(value = "/modifyAlias",method = RequestMethod.POST)
    public BaseResult modifyAlias(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String fuuid = param.get("fuuid");
        String alias = param.get("alias");
        return ucenterUserRelationService.modifyAlias(uuid,fuuid,alias);
    }

    //修改关系分组
    @ValidateLoginAspect
    @RequestMapping(value = "/modifyRelation",method = RequestMethod.POST)
    public BaseResult modifyRelation(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String fuuid = param.get("fuuid");
        byte relationType = Byte.valueOf(param.get("relation_type"));
        return ucenterUserRelationService.modifyRelation(uuid,fuuid,relationType);
    }

    //发送修改验证消息
    @ValidateLoginAspect
    @RequestMapping(value = "/sendModifyMessage",method = RequestMethod.POST)
    public BaseResult sendModifyMessage(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String fuuid = param.get("fuuid");
        byte relationType = Byte.valueOf(param.get("relation_type"));

        return ucenterUserRelationService.sendModifyRelationMessage(uuid,fuuid,relationType);
    }

    //拒绝修改
    @ValidateLoginAspect
    @RequestMapping(value = "/rejectModify",method = RequestMethod.POST)
    public BaseResult rejectModify(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String fuuid = param.get("fuuid");
        byte relationType = Byte.valueOf(param.get("relation_type"));

        return ucenterUserRelationService.rejectModifyRelation(uuid,fuuid,relationType);
    }
}
