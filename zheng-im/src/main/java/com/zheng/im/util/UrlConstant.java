package com.zheng.im.util;

/**
 * Created by linlinyeyu on 2017/12/13.
 */
public class UrlConstant {
    public static final String appKey = "cdd3e634db1c185917b785541cacd521";
    public static final String appSecret = "568afa5f9ad5";
    //注册url
    public static final String regUrl = "https://api.netease.im/nimserver/user/create.action";
    //加好友url
    public static final String addUrl = "https://api.netease.im/nimserver/friend/add.action";
    //获取好友关系url
    public static final String getUrl = "https://api.netease.im/nimserver/friend/get.action";
    //获取好友名片url
    public static final String cardUrl = "https://api.netease.im/nimserver/user/getUinfos.action";
    //更新idUrl
    public static final String updIdUrl = "https://api.netease.im/nimserver/user/update.action";
    //更新获取tokenUrl
    public static final String updAndGetUrl = "https://api.netease.im/nimserver/user/refreshToken.action";
    //解禁IdUrl
    public static final String unBlockUrl = "https://api.netease.im/nimserver/user/unblock.action";
    //封禁url
    public static final String blockUrl = "https://api.netease.im/nimserver/user/block.action";
    //更新用户名片url
    public static final String updUserUrl = "https://api.netease.im/nimserver/user/updateUinfo.action";
    //设置推送urk
    public static final String destToAppUrl = "https://api.netease.im/nimserver/user/setDonnop.action";
    //更新好友url
    public static final String updFriendUrl = "https://api.netease.im/nimserver/friend/update.action";
    //删除好友url
    public static final String delFriendUrl = "https://api.netease.im/nimserver/friend/delete.action";
    //设置黑名单静音
    public static final String setSpecialUrl = "https://api.netease.im/nimserver/user/setSpecialRelation.action";
    //获取黑名单url
    public static final String specialUrl = "https://api.netease.im/nimserver/user/listBlackAndMuteList.action";
    //发送普通消息url
    public static final String msgUrl = "https://api.netease.im/nimserver/msg/sendMsg.action";
    //批量发送点对点url
    public static final String batchMsgUrl = "https://api.netease.im/nimserver/msg/sendBatchMsg.action";
    //自定义消息url
    public static final String customMsgUrl = "https://api.netease.im/nimserver/msg/sendAttachMsg.action";
    //批量点对点自定义消息
    public static final String batchCustomUrl = "https://api.netease.im/nimserver/msg/sendBatchAttachMsg.action";
    //上传文件string
    public static final String uploadFileStrUrl = "https://api.netease.im/nimserver/msg/upload.action";
    //上传文件multi方式
    public static final String uploadMultiFileUrl = "https://api.netease.im/nimserver/msg/fileUpload.action";
    //撤回消息url
    public static final String recallUrl = "https://api.netease.im/nimserver/msg/recall.action";
    //广播消息
    public static final String broadUrl = "https://api.netease.im/nimserver/msg/broadcastMsg.action";
    //创建群
    public static final String groupUrl = "https://api.netease.im/nimserver/team/create.action";
    //拉人入群
    public static final String addUserGroupUrl = "https://api.netease.im/nimserver/team/add.action";
    //踢人出群
    public static final String kickUserUrl = "https://api.netease.im/nimserver/team/kick.action";
    //解散群
    public static final String removeUrl = "https://api.netease.im/nimserver/team/remove.action";
    //编辑群
    public static final String editGroupUrl = "https://api.netease.im/nimserver/team/update.action";
    //群信息与成员列表查询
    public static final String groupUserUrl = "https://api.netease.im/nimserver/team/query.action";
    //移交群主
    public static final String changeOwnerUrl = "https://api.netease.im/nimserver/team/changeOwner.action";
    //任命管理员
    public static final String addManagerUrl = "https://api.netease.im/nimserver/team/addManager.action";
    //移除管理员
    public static final String removeMangerUrl = "https://api.netease.im/nimserver/team/removeManager.action";
    //获取用户所有群信息
    public static final String userGroupUrl = "https://api.netease.im/nimserver/team/joinTeams.action";
    //设置群昵称
    public static final String groupNickUrl = "https://api.netease.im/nimserver/team/updateTeamNick.action";
    //修改消息提醒开关
    public static final String changeGroupNotify = "https://api.netease.im/nimserver/team/muteTeam.action";
    //禁言群成员
    public static final String muteUserUrl = "https://api.netease.im/nimserver/team/muteTlist.action";
    //主动退群
    public static final String leaveUrl = "https://api.netease.im/nimserver/team/leave.action";
    //全体禁言
    public static final String muteAllUrl = "https://api.netease.im/nimserver/team/muteTlistAll.action";
    //禁言用户列表
    public static final String muteUserListUrl = "https://api.netease.im/nimserver/team/listTeamMute.action";
}
