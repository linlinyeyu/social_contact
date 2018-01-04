package com.zheng.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class YZLHttpClientFactory {
	
	private static Map<HttpType, YZLHttpClient> clients = new HashMap<HttpType, YZLHttpClient>();
	
	public static YZLHttpClient create(){
		return create(HttpType.OKHttp);
	}
	
	public static YZLHttpClient create(HttpType type){
		if(clients.get(type) == null){
			YZLHttpClient client = null;
			switch (type) {
			case OKHttp:
				client = new OkHttpClientProxy();
				break;
			case Mine:
				client = new MineHttpClient();
				break;
			}
			clients.put(type, client);
		}
		return clients.get(type);
	}
	
	public static enum HttpType{
		OKHttp,Mine
	}

	public static void main(String[] args){
		JSONObject result = YZLHttpClientFactory.create().sendRequestDecodeByJSON("http://xincheng.zertone1.com/");
		System.out.println(JSON.toJSONString(result));
	}
}
