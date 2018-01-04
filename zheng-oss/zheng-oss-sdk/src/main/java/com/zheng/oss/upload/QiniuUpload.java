package com.zheng.oss.upload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zheng.common.util.IOUtil;
import com.zheng.common.util.PropertiesFileUtil;
import com.zheng.common.util.TimeUtil;
import com.zheng.common.util.WowCollections;
import com.zheng.oss.common.constant.OssResult;
import com.zheng.oss.upload.bean.FileEntity;
import com.zheng.oss.upload.bean.FileException;
import com.zheng.oss.upload.bean.Token;
import org.apache.velocity.texen.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static org.apache.zookeeper.ZooDefs.OpCode.auth;

public class QiniuUpload extends Upload{
	
	private UploadManager uploadManager;

	private Logger logger = LoggerFactory.getLogger(QiniuUpload.class);
	
	private Token token;



	public FileEntity uploadByStream(InputStream is, String filename){

		if(filename.contains("address")&&filename.contains("json"))
			filename = this.file.getDir()+"/"+filename;
		else
        	filename = this.dir.getDir() + "/" + filename;
		String response = "";

		Token token = this.generateToken();
		
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
		int length = 100;
		byte[] buff = new byte[length]; 
		int rc = 0; 
		try {
			while ((rc = is.read(buff, 0, length)) > 0) { 
				swapStream.write(buff, 0, rc); 
			}
			swapStream.flush();
		} catch (IOException e1) {
			throw new FileException("file transfer byte array failed");
		} 
		try {
			logger.info(filename);
			response = uploadManager.put(swapStream.toByteArray(), filename, token.getToken()).bodyString();
			System.out.println(response);
		} catch (QiniuException e) {
			logger.error(e.getMessage());
			throw new FileException(e.getMessage());
		}
		IOUtil.close(swapStream);
		JSONObject object = JSON.parseObject(response);
		FileEntity fileEntity = new FileEntity();
		String name = object.getString("key");
		int w = object.getIntValue("w");
		int h = object.getIntValue("h");
		fileEntity.setName(name);
		String prefix = this.getPrefix();
		fileEntity.setUrl(prefix + name);
		fileEntity.setWidth(w);
		fileEntity.setHeight(h);
		return fileEntity;
	}

	@Override
	public String getSuffix(int width , int height) {
		return "?imageView2/2/w/" + width + "/h/" + height;
	}

	@Override
	public String getSuffix() {
		return PropertiesFileUtil.getInstance("oss-config").get("qiniu.oss.suffix");
	}

	@Override
	public String getPrefix() {
		return "http://"
				+ PropertiesFileUtil.getInstance("oss-config").get("qiniu.oss.domain") + "/";
	}

	public Token generateToken(){
		System.out.println("generateToken");
		if(token == null || token.getDeadline() < System.currentTimeMillis() ){
			uploadManager = new UploadManager();
			String ak = PropertiesFileUtil.getInstance("oss-config").get("qiniu.oss.access_key");

			String sk = PropertiesFileUtil.getInstance("oss-config").get("qiniu.oss.secret_key");

			String bucket = PropertiesFileUtil.getInstance("oss-config").get("qiniu.oss.bucket");

			Auth auth = Auth.create(ak, sk);
			StringMap map = new StringMap();
			map.put("returnBody", JSON.toJSONString(WowCollections.map("name" , "$(fname)", "key" , "$(key)", "size", "$(fsize)","w","$(imageInfo.width)","h","$(imageInfo.height)","hash","$(etag)")));
			String token1 = auth.uploadToken(bucket, null, 3600, map);
			token = new Token(token1, TimeUtil.timeMillsChange(System.currentTimeMillis(), Calendar.HOUR_OF_DAY, 1));
		}

		return token;
	}

}
