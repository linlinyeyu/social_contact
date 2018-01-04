package com.zheng.api.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import com.zheng.address.dao.model.Address;
import com.zheng.address.service.AddressService;
import com.zheng.api.server.aop.ValidateLoginAspect;
import com.zheng.common.base.BaseApiController;
import com.zheng.common.base.BaseResult;
import com.zheng.common.constants.AuditConstants;
import com.zheng.common.util.*;
import com.zheng.sms.enums.SmsTemplate;
import com.zheng.user.dao.model.UcenterUser;
import com.zheng.user.dao.model.UcenterUserIdentity;
import com.zheng.user.dao.model.UcenterUserLog;
import com.zheng.user.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.github.pagehelper.util.MetaObjectUtil.method;
import static com.sun.tools.doclint.Entity.nu;
import static com.sun.tools.doclint.Entity.para;


/**
 * Created by linlinyeyu on 2017/12/14.
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseApiController{

    @Autowired
    private UcenterUserService ucenterUserService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private UcenterUserIdentityService ucenterUserIdentityService;

    @Autowired
    private AddressService addressService;


    @RequestMapping(value = "/loginPasswd" ,method = RequestMethod.POST)
    public BaseResult loginPasswd(@RequestBody Map<String, String> map){

        String phone = map.get("phone");
        String password = map.get("password");
        String jpushToken = map.get("jpush_token");
        String deviceId = map.get("device_id");
        String agent = getAgent();
        int terminal = paramAsInt(headers(), "terminal", 0);

        if(!ValidatorUtil.validPhone(phone)){
            return FormatResponseUtil.error(ErrorCodeEnum.PHONE_NOT_MATCH);
        }


        UcenterUser ucenterUser = ucenterUserService.selectByPhone(phone);

        if(ucenterUser == null){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_OR_PASSWD_ERROR);
        }
        if(!ucenterUser.getPassword().equals(password)){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_OR_PASSWD_ERROR);
        }

        Map<String, Object> user = ucenterUserService.login(ucenterUser.getUserId(), getIp(), deviceId,agent, terminal, jpushToken );

        return FormatResponseUtil.formatResponse(WowCollections.map("user", user));
    }

    @RequestMapping(value = "/username",method = RequestMethod.GET)
    public BaseResult selectName(@RequestParam String uuid){
        String name = ucenterUserService.selectName(uuid);
        return new BaseResult(0,"请求成功",name);
    }


    @RequestMapping(value = "/loginSms" ,method = RequestMethod.POST)
    public BaseResult loginSms(@RequestBody Map<String, String> map){

        String phone = map.get("phone");
        String jpushToken = map.get("jpush_token");
        String deviceId = map.get("device_id");
        String verifyCode = map.get("verify_code");
        String agent = header("User-Agent");
        int terminal = paramAsInt(headers(), "terminal", 0);

        boolean isAuthed = verifyCodeService.verify(phone, verifyCode, SmsTemplate.LOGIN);

        if(!isAuthed){
            return FormatResponseUtil.error(ErrorCodeEnum.SMS_ERROR);
        }
        JSONObject jsonObject = new JSONObject();
        UcenterUser user = ucenterUserService.selectByPhone(phone);
        if(user == null){
            jsonObject.put("is_regist", 0);
            jsonObject.put("phone", phone);
        }else{
            jsonObject.put("is_regist", 1);
            Map<String, Object> ucenterUser = ucenterUserService.login(user.getUserId(),getIp(), deviceId, agent, terminal, jpushToken);
            if(ucenterUser == null){
                return FormatResponseUtil.error(ErrorCodeEnum.USER_NOT_ACTIVE);
            }
            jsonObject.put("user", ucenterUser);
        }

        return FormatResponseUtil.formatResponse(jsonObject);
    }

    //注册
    @ApiOperation("注册接口，在此之前需要调用loginSms接口")
    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public BaseResult regist(@RequestBody Map<String, String> map){
        String phone = map.get("phone");
        String jpushToken = map.get("jpush_token");
        String deviceId = map.get("device_id");
        String password = map.get("password");
        String agent = header("User-Agent");
        int terminal = paramAsInt(headers(), "terminal", 0);
        String nickname = map.get("nickname");
        String avatar = map.get("avatar");

//        boolean isActive = verifyCodeService.isActive(phone, SmsTemplate.LOGIN);
//
//        if(!isActive){
//            return FormatResponseUtil.error(ErrorCodeEnum.SMS_EXPIRED);
//        }

        if(StringUtil.isEmpty(nickname)){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_NAME_EMPTY);
        }

        if(!ValidatorUtil.validName(nickname)){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_NAME_ERROR);
        }

        if(StringUtil.isEmpty(password)){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_PASSWORD_EMPTY);
        }
        if(password.length() != 32){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_PASSWORD_ERROR);
        }


        UcenterUser ucenterUser = new UcenterUser();
        ucenterUser.setPhone(phone);
        ucenterUser.setPassword(password);
        ucenterUser.setAvatar(avatar);
        ucenterUser.setNickname(nickname);
        BaseResult result = ucenterUserService.regist(ucenterUser);

        //如果注册失败，则直接返回
        if(!FormatResponseUtil.isSuccess(result)){
            return result;
        }
        Integer userId = (Integer) result.getData();


        Map<String, Object> user = ucenterUserService.login(userId, getIp(), deviceId, agent, terminal, jpushToken);
        if(user == null){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_NOT_ACTIVE);
        }

        return FormatResponseUtil.formatResponse(WowCollections.map("user", user));
    }

    @ApiOperation("忘记密码，在此之前需要调用sendCode接口")
    @RequestMapping( value = "forgetPwd", method = RequestMethod.POST, consumes = "application/json")
    public BaseResult forgetPwd(@RequestBody Map<String, String> map){

        String phone = map.get("phone");

        String password = map.get("password");

        String verifyCode = map.get("verify_code");

        boolean isVerified = verifyCodeService.verify(phone,verifyCode, SmsTemplate.FORGET);

        if(!isVerified){
            return FormatResponseUtil.error(ErrorCodeEnum.SMS_EXPIRED);
        }

        UcenterUser user = ucenterUserService.selectByPhone(phone);
        if(user == null){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_OR_PASSWD_ERROR);
        }

        UcenterUser update = new UcenterUser();
        update.setPassword(password);
        update.setUserId(user.getUserId());
        ucenterUserService.updateAndClearCache(update);

        return FormatResponseUtil.formatResponse();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ValidateLoginAspect
    public BaseResult detail(){

        int userId = getUserId();

        Map<String, Object> user = ucenterUserService.selectAppUserDetail(userId);

        return FormatResponseUtil.formatResponse(WowCollections.map("user", user));
    }


    @ApiOperation("查找用户是否进行过审核信息，如果有，则会有identity字段，如果没有则为空")
    @RequestMapping(value = "/identity", method = RequestMethod.GET)
    @ValidateLoginAspect
    public BaseResult identityGet(){

        int userId = getUserId();

        UcenterUserIdentity ucenterUserIdentity = ucenterUserIdentityService.selectLastIdentity(userId);

        if(ucenterUserIdentity == null){
            return FormatResponseUtil.formatResponse();
        }
        String message = null;

        if(ucenterUserIdentity.getState() == AuditConstants.FAILED){
            message = ucenterUserIdentity.getReply();
        }


        return FormatResponseUtil.formatResponse(WowCollections.map("identity", ucenterUserIdentity, "message", message));
    }

    @ApiOperation("申请身份认证")
    @ValidateLoginAspect
    @RequestMapping(value = "/identity", method = RequestMethod.POST)
    public BaseResult identityPost(@RequestBody UcenterUserIdentity param){

        int userId = getUserId();
        UcenterUserIdentity ucenterUserIdentity = ucenterUserIdentityService.selectPassedOrAuditIdentity(userId);

        if(ucenterUserIdentity != null){
            return FormatResponseUtil.error(ErrorCodeEnum.IDENTITY_DUPLICATE);
        }

        if(!ValidatorUtil.validIdentityCard(param.getIdentityCard())){
            return FormatResponseUtil.error(ErrorCodeEnum.IDENTITY_CARD_EMPTY);
        }
        if(!ValidatorUtil.validName(param.getRealname())){
            return FormatResponseUtil.error(ErrorCodeEnum.IDENTITY_NAME_ERROR);
        }

        if(param.getSex() == null || param.getSex() <= 0 || param.getSex() > 2){
            return FormatResponseUtil.error(ErrorCodeEnum.IDENTITY_SEX_EMPTY);
        }
        if(StringUtil.isEmpty(param.getFrontImage())){
            return FormatResponseUtil.error(ErrorCodeEnum.IDENTITY_FRONT_EMPTY);
        }

        if(StringUtil.isEmpty(param.getBehindImage())){
            return FormatResponseUtil.error(ErrorCodeEnum.IDENTITY_BEHIND_EMPTY);
        }

        param.setUserId(getUserId());
        param.setCreateTime(new Date().getTime());
        param.setState(AuditConstants.AUDITING);

        int rows = ucenterUserIdentityService.insertSelective(param);

        if(rows == 0){
            return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
        }
        return FormatResponseUtil.formatResponse();
    }

    @ApiOperation("修改用户信息")
    @RequestMapping(value = "editUser", method = RequestMethod.POST)
    @ValidateLoginAspect
    public BaseResult editUser(@RequestBody Map<String, String> params){
        int userId = getUserId();
        String avatar = params.get("avatar");

        String nickname = params.get("nickname");

        int cityId = paramAsInt(params, "city_id", 0);
        int provinceId = paramAsInt(params,"province_id", 0);
        UcenterUser update = new UcenterUser();
        update.setUserId(userId);

        if(!StringUtil.isEmpty(avatar)){
            update.setAvatar(avatar);
        }

        if(!StringUtil.isEmpty(nickname)){
            if(!ValidatorUtil.validName(nickname)){
                return FormatResponseUtil.error(ErrorCodeEnum.USER_NAME_ERROR);
            }
            update.setNickname(nickname);
        }

        if(cityId != 0){
            Address address = addressService.selectByPrimaryKey(cityId);
            if(address != null){
                update.setArea(address.getName());
                update.setCityId(cityId);
            }
        }
        if(provinceId != 0){
            update.setProvinceId(provinceId);
        }

        int rows = ucenterUserService.updateAndClearCache(update);

        if(rows == 0){
            return FormatResponseUtil.error(ErrorCodeEnum.USER_PARAM_EMPTY);
        }

        Map<String, Object> user = ucenterUserService.selectAppUserDetail(userId);

        return FormatResponseUtil.formatResponse(WowCollections.map("user", user));
    }

    @ApiOperation("发送旧手机验证码")
    @RequestMapping(value = "/sendOldPhoneCode", method = RequestMethod.POST)
    @ValidateLoginAspect
    public BaseResult sendOldPhoneCode(){
        UcenterUser user = (UcenterUser) getUser();
        return verifyCodeService.sendCode(user.getPhone(), SmsTemplate.VERIFYPHONE);
    }

    @ApiOperation("验证旧手机验证码")
    @ValidateLoginAspect
    @RequestMapping(value = "/verifyOldPhone", method = RequestMethod.POST)
    public BaseResult verifyOldPhone(@RequestBody Map<String, String> param) {
        String verifyCode = param.get("verify_code");

        if(StringUtil.isEmpty(verifyCode)){
            return FormatResponseUtil.error(ErrorCodeEnum.SMS_EMPTY);
        }
        UcenterUser user = (UcenterUser) getUser();
        boolean isVerified = verifyCodeService.verify(user.getPhone(), verifyCode, SmsTemplate.VERIFYPHONE);

        return isVerified ? FormatResponseUtil.formatResponse() : FormatResponseUtil.error(ErrorCodeEnum.SMS_ERROR);
    }

    @ApiOperation("验证新手机验证码，并对旧手机做处理")
    @ValidateLoginAspect
    @RequestMapping(value = "/editPhone", method = RequestMethod.POST)
    public BaseResult editPhone(@RequestBody Map<String, String> param) {
        String verifyCode = param.get("verify_code");
        String phone = param.get("phone");

        if(!ValidatorUtil.validPhone(phone)){
            return FormatResponseUtil.error(ErrorCodeEnum.PHONE_NOT_MATCH);
        }

        if(StringUtil.isEmpty(verifyCode)){
            return FormatResponseUtil.error(ErrorCodeEnum.SMS_EMPTY);
        }
        UcenterUser user = (UcenterUser) getUser();
        boolean isActive = verifyCodeService.isActive(user.getPhone(), SmsTemplate.VERIFYPHONE);

        if(!isActive){
            return FormatResponseUtil.error(ErrorCodeEnum.OLD_PHONE_EXPIRED);
        }
        boolean isVerified =  verifyCodeService.verify(phone, verifyCode, SmsTemplate.EDITPHONE);

        if(!isVerified){
            return FormatResponseUtil.error(ErrorCodeEnum.SMS_ERROR);
        }

        UcenterUser update = new UcenterUser();
        update.setUserId(user.getUserId());
        update.setPhone(phone);
        int rows = ucenterUserService.updateAndClearCache(update);
        return rows > 0 ? FormatResponseUtil.formatResponse(WowCollections.map("phone", phone))
                : FormatResponseUtil.formatResponse(ErrorCodeEnum.FAILED);
    }

    //搜索好友
    @ValidateLoginAspect
    @RequestMapping(value = "/selectFriend",method = RequestMethod.GET)
    public BaseResult selectFriend(@RequestParam String phone){
        String uuid = getUuid();
       return ucenterUserService.selectFriend(phone,uuid);
    }

    //匹配通讯录
    @ValidateLoginAspect
    @RequestMapping(value = "/matchMail")
    public BaseResult matchMailList(@RequestBody Map<String,String> param){
        String uuid = getUuid();
        String phones = param.get("phones");
        return ucenterUserService.matchMailList(uuid,phones);
    }
}
