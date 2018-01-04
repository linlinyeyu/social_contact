package com.zheng.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.List;

public abstract class YZLHttpClient {
	
	public String sendRequest(String url, JSONObject body, HttpMethod method){
		return this.sendRequest(url, body, null , method);
	}
	
	public String sendRequest(String url, JSONObject body, List<YZLHeader> headers){
		return this.sendRequest(url, body, headers , HttpMethod.GET);
	}
	
	public String sendRequest(String url ,JSONObject body){
		return this.sendRequest(url, body,null , HttpMethod.GET);
	}
	
	public String sendRequest(String url){
		return this.sendRequest(url, null,  null , HttpMethod.GET);
	}
	
	public JSONObject sendRequestDecodeByJSON(String url){
		String text = this.sendRequest(url);
		return JSON.parseObject(text);
	}
	public JSONObject sendRequestDecodeByJSON(String url , JSONObject body){
		String text = this.sendRequest(url,body);
		return JSON.parseObject(text);
	}
	
	public JSONObject sendRequestDecodeByJSON(String url, JSONObject body, List<YZLHeader> headers){
		String text = this.sendRequest(url, body , headers);
		return JSON.parseObject(text);
	}
	
	public JSONObject sendRequestDecodeByJSON(String url, JSONObject body, List<YZLHeader> headers, HttpMethod method){
		String text = this.sendRequest(url, body , headers, method);
		return JSON.parseObject(text);
	}
	
	public JSONObject sendRequestDecodeByJSON(String url, JSONObject body, HttpMethod method){
		String text = this.sendRequest(url,body, method);
		return JSON.parseObject(text);
	}
	
	
	
	public abstract String sendRequest(String url, JSONObject body, List<YZLHeader> headers, HttpMethod method );
	
	public abstract void sendRequestAsync(String url, JSONObject body, List<YZLHeader> headers, HttpMethod method , Callback callback);
	
	public String fileUpload(String url , File file, JSONObject body, HttpMethod method){
		return this.fileUpload(url, file, body, null, method);
	}
	
	public String fileUpload(String url , File file, JSONObject body, List<YZLHeader> headers){
		return this.fileUpload(url, file, body, headers , HttpMethod.POST);
	}
	public String fileUpload(String url ,File file, JSONObject body){
		return this.fileUpload(url, file, body, null, HttpMethod.POST);
	}
	
	public String fileUpload(String url ,File file){
		return this.fileUpload(url, file, null, null, HttpMethod.POST);
	}
	
	
	public abstract String fileUpload(String url , File file, JSONObject body, List<YZLHeader> headers, HttpMethod method  );

    public abstract String sendRequestWithXml(String url, String body, HttpMethod method);



}
