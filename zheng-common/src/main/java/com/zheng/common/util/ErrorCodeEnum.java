package com.zheng.common.util;

/**
 * Created by linlinyeyu on 2017/12/14.
 */
public enum ErrorCodeEnum {
    SUCCESS(0,"请求成功"),

    USER_TOKEN_EXPIRE(-99, "token已过期"),

    FAILED(-9000,"请求失败"),
    SIGN_ERROR(-9001,"签名错误"),
    PARAM_ERROR(-9002, "参数不匹配"),
    DELETE_ERROR(-9800, "删除失败"),
    ID_EMPTY (-9900, "请传入ID"),




    SMS_EMPTY(-10002, "验证码错误"),
    SMS_ERROR(-10003, "验证码错误"),
    SMS_EXPIRED(-10004,"验证码已过期"),
    OLD_PHONE_EXPIRED(-10005,"原手机验证码已失效，请重发"),
    PHONE_NOT_MATCH(-10004,"手机号格式不正确"),
    USER_OR_PASSWD_ERROR(-10005, "账号或密码错误"),
    USER_NOT_ACTIVE(-10006, "用户已被冻结，如有问题请联系客服"),
    USER_PHONE_DUPLICATE(-10007, "此手机号已被使用"),
    USER_NAME_EMPTY(-10008,"用户昵称未传"),
    USER_NAME_ERROR(-10009,"昵称只支持中文和英文"),
    USER_PASSWORD_EMPTY(-10008,"用户密码未传"),
    USER_PASSWORD_ERROR(-10009,"用户密码格式不正确"),

    IDENTITY_NAME_EMPTY(-11001,"真实姓名未传"),
    IDENTITY_NAME_ERROR(-11002,"姓名只支持中文和英文"),
    IDENTITY_SEX_EMPTY(-11003,"性别未传"),
    IDENTITY_CARD_EMPTY(-11004,"身份证未传"),
    IDENTITY_FRONT_EMPTY(-11005,"正面照未传"),
    IDENTITY_BEHIND_EMPTY(-11006,"反面照未传"),
    IDENTITY_DUPLICATE(-11007,"已申请，请耐心等待"),

    TIMELINE_EMPTY(-14001, "请传入朋友圈信息"),
    TIMELINE_BODY_EMPTY(-14002, "请传入朋友圈内容"),
    TIMELINE_BODY_ERROR(-14003, "内容格式有误"),
    TIMELINE_USER_EMPTY(-14004, "请传入用户信息"),
    TIMELINE_MEDIA_EMPTY(-14005, "请传入朋友圈类型"),
    TIMELINE_PUBLISH_FAIL(-14006, "发布失败"),
    TIMELINE_RELATION_ERROR(-14007, "请传入朋友圈发布范围"),

    TIMELINE_LAUD_ERROR(-14008, "您已点赞，请勿重复"),
    TIMELINE_COMMENT_EMPTY(-14009, "请输入评论内容"),
    TIMELINE_COMMENT_DUPLICATE(-14010, "无法评论自己"),

    USER_PARAM_EMPTY(-12001,"请传入修改信息"),

    ADDRESS_NAME_EMPTY(-13001,"请传入名称"),
    ADDRESS_CANNOT_CREATE_PROVINCE(-13002, "无法创建省级地址"),
    ADDRESS_PARENT_EMPTY(-13003, "找不到父级地址"),
    ADDRESS_CITY_PINYIN_EMPTY(-13004, "请输入城市的拼音"),

    ADDRESS_PARAM_EMPTY(-13005, "参数不能为空"),
    ADDRESS_ID_EMPTY(-13006, "请传入修改地址的id"),
    ADDRESS_EMPTY(-13007, "指定地址不存在"),
    ADDRESS_PROVINCE_ERROR(-13008, "不允许修改省份信息"),
    ADDRESS_HAS_SON(-13009, "有下级子菜单，无法删除"),


    ADD_FRIEND_ERROR(-20001,"添加好友失败"),
    UPD_FRIEND_FAIL(-20002,"更新好友失败"),
    DEL_FRIEND_FAIL(-20003,"删除好友失败"),
    REJECT_FRIEND_FAIL(-20004,"拒绝好友失败"),
    UPD_RELATION_FAIL(-20005,"更新好友关系失败"),

    CREATE_GROUP_FAIL(-30001,"创建群聊失败"),
    ADD_GROUP_USER_FAIL(-30002,"添加群成员失败"),
    KICK_USER_FAIL(-30003,"踢除失败"),
    UPD_GROUP_FAIL(-30004,"更新群失败"),
    REMOVE_GROUP_FAIL(-30005,"解散群失败"),
    UPD_GROUP_LEADER_FAIL(-30006,"更换群主失败"),
    UPD_GROUP_ALIAS_FAIL(-30007,"更新群昵称失败"),
    LEADER_NOT_CHANGE(-30008,"请先移交群主"),
    LEAVE_GROUP_FAIL(-30009,"退出失败"),
    CREATE_TEMP_GROUP_FAIL(-30010,"拉取面对面群消息失败"),
    QUIT_AROUND_GROUP_FAIL(-30011,"退出面对面群失败"),
    UPD_MANAGER_FAIL(-30012,"设置管理员失败"),
    MEMBER_EXCEED(-30013,"一次邀请成员不能超过200"),
    SET_TOP_FAIL(-30014,"设置置顶失败"),
    SET_MAIL_FAIL(-30015,"设置通讯录失败"),
    SET_MUTE_TEAM_FAIL(-30016,"设置免打扰失败"),
    SEND_FAIL(-30017,"发送失败"),

    REGIST_FAIL(-40001,"注册失败");


    private String value;
    private int key;

    private ErrorCodeEnum(int key,String value){
        this.value = value;
        this.key = key;
    }

    public int getKey(){
        return this.key;
    }

    public String getValue(){
        return this.value;
    }

    public static String getValue(int key){
        for (ErrorCodeEnum typeEnum:ErrorCodeEnum.values()){
            if (typeEnum.getKey() == key){
                return typeEnum.getValue();
            }
        }
        return null;
    }

    public static ErrorCodeEnum get(int key){
        for (ErrorCodeEnum typeEnum:ErrorCodeEnum.values()){
            if (typeEnum.getKey() == key){
                return typeEnum;
            }
        }
        return null;
    }
}
