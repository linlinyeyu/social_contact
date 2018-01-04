package com.zheng.im.base;

import com.zheng.common.base.BaseResult;
import com.zheng.im.domain.message.*;

import java.io.File;
import java.util.Map;

/**
 * Created by linlinyeyu on 2017/12/13.
 */
public interface MessageBase {
    /**
     * 发送普通消息
     * @param imMessage
     * @return
     */
    BaseResult sendOriginMessage(ImMessage imMessage);

    /**
     * 批量发送信息
     * @param batchMessage
     * @return
     */
    BaseResult batchSendMsg(BatchMessage batchMessage);

    /**
     * 发送自定义系统消息
     * @param customMessage
     * @return
     */
    BaseResult sendCustomMsg(CustomMessage customMessage);

    /**
     * 批量发送自定义消息
     * @param batchCustomMessage
     * @return
     */
    BaseResult batchSendCustomMsg(BatchCustomMessage batchCustomMessage);

    /**
     * 上传文件
     * @param content base64串
     * @return
     */
    BaseResult uploadFile(String content);

    /**
     * multi形式上传文件
     * @param content
     * @return
     */
    BaseResult uploadMultiFile(File content);

    /**
     * 撤回消息
     * @param recallMessage
     * @return
     */
    BaseResult recallMsg(RecallMessage recallMessage);

    /**
     * 发送广播消息
     * @param broadCastMessage
     * @return
     */
    BaseResult broadcastMsg(BroadCastMessage broadCastMessage);
}
