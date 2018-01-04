package com.zheng.im.wyim;

import com.zheng.common.base.BaseResult;
import com.zheng.common.util.FormatResponseUtil;
import com.zheng.im.base.HttpBase;
import com.zheng.im.base.MessageBase;
import com.zheng.im.domain.message.*;
import com.zheng.im.util.FormatResultUtil;
import com.zheng.im.util.HttpUtil;
import com.zheng.im.util.UrlConstant;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by linlinyeyu on 2017/12/13.
 */

public class WyMessageImpl implements MessageBase {

    private HttpBase httpBase;

    public WyMessageImpl(HttpBase httpBase){
        this.httpBase = httpBase;
    }

    @Override
    public BaseResult sendOriginMessage(ImMessage imMessage) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.msgUrl);
        String entity = HttpUtil.post(httpPost,imMessage);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult batchSendMsg(BatchMessage batchMessage) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.batchMsgUrl);
        String entity = HttpUtil.post(httpPost,batchMessage);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult sendCustomMsg(CustomMessage customMessage) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.customMsgUrl);
        String entity = HttpUtil.post(httpPost,customMessage);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult batchSendCustomMsg(BatchCustomMessage batchCustomMessage) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.batchCustomUrl);
        String entity = HttpUtil.post(httpPost,batchCustomMessage);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult uploadFile(final String content) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.uploadFileStrUrl);
        List<NameValuePair> list = new ArrayList<NameValuePair>(){{add(new BasicNameValuePair("content",content));}};
        String entity = HttpUtil.post(httpPost,list);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult uploadMultiFile(File content) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.uploadMultiFileUrl);
        httpPost.removeHeaders("Content-Type");
        MultipartEntity multipartEntity = new MultipartEntity();
        multipartEntity.addPart("content",new FileBody(content));
        httpPost.setEntity(multipartEntity);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String entity = EntityUtils.toString(response.getEntity());
            return FormatResultUtil.judgeResult(entity);
        } catch (IOException e) {
            e.printStackTrace();
            return FormatResponseUtil.formatResponse(-90002,"上传失败");
        }
    }

    @Override
    public BaseResult recallMsg(RecallMessage recallMessage) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.recallUrl);
        String entity = HttpUtil.post(httpPost,recallMessage);
        return FormatResultUtil.judgeResult(entity);
    }

    @Override
    public BaseResult broadcastMsg(BroadCastMessage broadCastMessage) {
        HttpPost httpPost = HttpUtil.setHeader(UrlConstant.broadUrl);
        String entity = HttpUtil.post(httpPost,broadCastMessage);
        return FormatResultUtil.judegResultMap(entity);
    }
}
