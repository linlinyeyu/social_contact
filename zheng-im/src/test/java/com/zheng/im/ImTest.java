package com.zheng.im;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zheng.common.base.BaseResult;
import com.zheng.im.base.ChatBase;
import com.zheng.im.base.UserBase;
import com.zheng.im.domain.group.AddUserGroup;
import com.zheng.im.domain.group.CreateGroup;
import com.zheng.im.domain.group.EditGroup;
import com.zheng.im.domain.message.*;
import com.zheng.im.domain.user.Friend;
import com.zheng.im.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linlinyeyu on 2017/12/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:chat.xml"})
public class ImTest {
    @Autowired
    private ChatBase chatBase;


    @Test
    public void imTest() {
        UserBase imbase =chatBase.getUserBase();


        Friend friend = new Friend();
        friend.setAccid("14194337");
        friend.setFaccid("14194337");
        friend.setType(1);
        friend.setMsg("我要加好友");

        User user = new User();
        user.setMobile("15720600671");
        user.setAccid("14194337");
        user.setName("王星");

        System.out.println(JSON.toJSONString(imbase.imRegister(user)));
    }

    @Test
    public void getFriend(){
        Friend friend = new Friend();
        friend.setAccid("12980662");
        friend.setUpdatetime(1512835200000L);
        BaseResult map = chatBase.getUserBase().getFriends(friend);
        System.out.println(JSON.toJSON(map));
    }

    @Test
    public void getUserCard(){
        UserBase imbase = chatBase.getUserBase();
        String[] data = new String[]{"12980662","13042789"};
        BaseResult map = imbase.getUserCard(data);
        System.out.println(JSONObject.toJSON(map));
    }

    //封禁id测试
    @Test
    public void blockTest(){
        BaseResult result = chatBase.getUserBase().blockUser("12980662");
        System.out.println(result);
    }

    //解禁测试
    @Test
    public void unblockTest(){
        BaseResult result = chatBase.getUserBase().unBlockUser("12980662");
        System.out.println(result);
    }
    //更新获取token测试
    @Test
    public void refreshTokenTest(){
        BaseResult map = chatBase.getUserBase().updAndGetUser("14194337");
        System.out.println(JSONObject.toJSONString(map));
    }

    /**
     * 更新用户名片测试
     */
    @Test
    public void udpUserTest(){
        User user = new User();
        user.setAccid("12980662");
        user.setName("atom");
        System.out.println(chatBase.getUserBase().udpUser(user));
    }
    /**
     * 设置桌面端移动端推送
     */
    @Test
    public void deskToApp(){
        System.out.println(chatBase.getUserBase().setDeskToApp("12980662","true"));
    }

    /**
     * 更新好友关系
     */
    @Test
    public void updFriend(){
        Friend friend = new Friend();
        friend.setAccid("12980662");
        friend.setFaccid("12980699");
        friend.setAlias("朋友");
        System.out.println(chatBase.getUserBase().updFriend(friend));
    }
    /*
    测试黑名单
     */
    @Test
    public void setSpecialTest(){
        System.out.println(chatBase.getUserBase().setSpecialRelation("12980662","12980699",1,0));
    }

    //获取黑名单列表
    @Test
    public void getSpecial(){
        System.out.println(JSON.toJSONString(chatBase.getUserBase().getSpecialRelationList("12980662")));
    }

    //发送普通消息
    @Test
    public void sendMsg(){
        ImMessage imMessage = new ImMessage();
        imMessage.setFrom("12980662");
        imMessage.setOpe(0);
        imMessage.setTo("12980699");
        imMessage.setType(0);
        Map<String,Object> body = new HashMap<String,Object>(){{put("msg","hello");}};
        imMessage.setBody(JSON.toJSONString(body));
        System.out.println(chatBase.getMessageBase().sendOriginMessage(imMessage));
    }

    //批量发送点对点消息
    @Test
    public void batchSendMsg(){
        BatchMessage batchMessage = new BatchMessage();
        batchMessage.setFromAccid("12980662");
        String[] accids = new String[]{"12980699"};
        batchMessage.setToAccids(JSON.toJSONString(accids));
        batchMessage.setType(0);
        Map<String,Object> body = new HashMap<String,Object>(){{put("msg","hello");}};
        batchMessage.setBody(JSON.toJSONString(body));
        System.out.println(chatBase.getMessageBase().batchSendMsg(batchMessage));
    }

    //系统自定义消息
    @Test
    public void customSend(){
        CustomMessage customMessage = new CustomMessage();
        customMessage.setFrom("12980662");
        customMessage.setMsgtype(0);
        customMessage.setTo("12980699");
        Map<String,Object> body = new HashMap<String,Object>(){{put("msg","hello");}};
        customMessage.setAttach(JSON.toJSONString(body));
        System.out.println(chatBase.getMessageBase().sendCustomMsg(customMessage));
    }

    //批量自定义消息
    @Test
    public void batchCustomSend(){
        BatchCustomMessage batchCustomMessage = new BatchCustomMessage();
        batchCustomMessage.setFromAccid("12980662");
        String[] accids = new String[]{"12980699"};
        batchCustomMessage.setToAccids(JSON.toJSONString(accids));
        Map<String,Object> body = new HashMap<String,Object>(){{put("msg","hello");}};
        batchCustomMessage.setAttach(JSON.toJSONString(body));
        System.out.println(chatBase.getMessageBase().batchSendCustomMsg(batchCustomMessage));
    }

    //上传文件
    @Test
    public void uploadStrFile(){
        System.out.println(chatBase.getMessageBase().uploadFile("xasssawwawaaa"));
    }

    //上传文件multi
    @Test
    public void uploadMulFile(){
        File file = new File("C:\\Users\\admin\\Desktop\\开发顺序.txt");
        System.out.println(chatBase.getMessageBase().uploadMultiFile(file));
    }

    //撤回消息
    @Test
    public void recallMst(){
        RecallMessage recallMessage = new RecallMessage();
        recallMessage.setDeleteMsgid("123452");
        recallMessage.setTimetag(new Date().getTime());
        recallMessage.setType(7);
        recallMessage.setFrom("12980662");
        recallMessage.setTo("12980699");
        System.out.println(chatBase.getMessageBase().recallMsg(recallMessage));
    }

    //广播消息
    @Test
    public void broadMsg(){
        BroadCastMessage broadCastMessage = new BroadCastMessage();
        broadCastMessage.setBody("aaaa");
        System.out.println(JSON.toJSONString(chatBase.getMessageBase().broadcastMsg(broadCastMessage)));
    }

    //创建群
    @Test
    public void createGroup(){
        CreateGroup createGroup = new CreateGroup();
        createGroup.setTname("测试第一群");
        createGroup.setOwner("12980662");
        String[] members = new String[]{"12980699"};
        createGroup.setMembers(JSON.toJSONString(members));
        createGroup.setMsg("给个面子");
        createGroup.setMagree(0);
        createGroup.setJoinmode(0);
        System.out.println(JSON.toJSONString(chatBase.getGroupBase().createGroup(createGroup)));
    }

    //拉人入群
    @Test
    public void addUserGroup(){
        AddUserGroup addUserGroup = new AddUserGroup();
        addUserGroup.setTid("230784117");
        addUserGroup.setOwner("12980662");
        addUserGroup.setMagree(0);
        String[] members = new String[]{"12980699"};
        addUserGroup.setMembers(JSON.toJSONString(members));
        addUserGroup.setMsg("给个面子");
        System.out.println(JSON.toJSONString(chatBase.getGroupBase().addUserGroup(addUserGroup)));
    }

    //踢人出群
    @Test
    public void kickUser(){
        System.out.println(chatBase.getGroupBase().kickUser("230784117","12980662","12980699"));
    }

    //解散群
    @Test
    public void remove(){
        System.out.println(chatBase.getGroupBase().remove("230784117","12980662"));
    }

    //编辑群
    @Test
    public void editGroup(){
        EditGroup editGroup = new EditGroup();
        editGroup.setTid("230784117");
        editGroup.setTname("宅群");
        editGroup.setOwner("12980662");
        System.out.println(chatBase.getGroupBase().editGroup(editGroup));
    }

    //获取群信息与成员
    @Test
    public void getGroupUser(){
        String data[] = new String[]{"230784117"};
        System.out.println(JSON.toJSONString(chatBase.getGroupBase().getGrouoUser(JSON.toJSONString(data),1)));
    }

    //移交群主
    @Test
    public void changeOwner(){
        System.out.println(chatBase.getGroupBase().changeOwner("230784117","12980699","12980662",2));
    }

    //增加管理员
    @Test
    public void addManager(){
        String[] data = new String[]{"12980699"};
        System.out.println(chatBase.getGroupBase().addManager("230784117","12980662",JSON.toJSONString(data)));
    }

    //移除管理员
    @Test
    public void removeManager(){
        String[] data = new String[]{"12980699"};
        System.out.println(chatBase.getGroupBase().removeManager("230784117","12980662",JSON.toJSONString(data)));
    }

    //获取用户所有群信息
    @Test
    public void getUserGroup(){
        System.out.print(JSON.toJSONString(chatBase.getGroupBase().getGroups("12980699")));
    }

    //修改群昵称
    @Test
    public void changeGroupNick(){
        System.out.print(chatBase.getGroupBase().updGroupNickname("230784117","12980662","12980699","妖艳群"));
    }

    //修改消息提醒
    @Test
    public void changeGroupNotify(){
        System.out.println(chatBase.getGroupBase().changeGroupNotify("230784117","12980699",2));
    }

    //禁言群成员
    @Test
    public void muteGroupUser(){
        System.out.println(chatBase.getGroupBase().muteGroupUser("230784117","12980662","12980699",1));
    }

    //主动退群
    @Test
    public void leave(){
        System.out.println(chatBase.getGroupBase().leave("230784117","12980699"));
    }

    //全体禁言
    @Test
    public void muteAll(){
        System.out.println(chatBase.getGroupBase().muteAll("230784117","12980662","true"));
    }

    //禁言用户列表
    @Test
    public void getMuteUser(){
        System.out.print(JSON.toJSONString(chatBase.getGroupBase().getMuteUser("230784117","12980662")));
    }
}
