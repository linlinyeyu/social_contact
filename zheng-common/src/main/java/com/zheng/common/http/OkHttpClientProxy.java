package com.zheng.common.http;

import com.alibaba.fastjson.JSONObject;

import okhttp3.*;
import okhttp3.Request.Builder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class OkHttpClientProxy  extends YZLHttpClient{

	@Override
	public String sendRequest(String url, JSONObject body,
			List<YZLHeader> headers, HttpMethod method) {
		
		OkHttpClient client = new OkHttpClient();
		Builder requestBuilder = new Request.Builder().url(url);
		boolean isJson = false;
		if(headers != null){
			for (YZLHeader header : headers) {
				requestBuilder.addHeader(header.getName(), header.getValue());
				if("content-type" .equals(header.getName().toLowerCase())){
					isJson = header.getValue().contains("application/json");
				}
			}
		}
		RequestBody requestBody = null;
		
		if(body != null){
			if(isJson){
				requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body.toJSONString());
			}else{
				FormBody.Builder formBuilder = new FormBody.Builder();
				Set<Entry<String, Object>> entries =  body.entrySet();
				for (Entry<String, Object> entry : entries) {
					if(entry.getValue()!= null){
						formBuilder.addEncoded(entry.getKey(), entry.getValue().toString());
					}
				}
				requestBody = formBuilder.build();
			}
			
			
			
		}
		
		Request request = null;
		switch (method) {
		case GET:
			request = requestBuilder.get().build();
			break;
		case POST:
			request = requestBuilder.post(requestBody).build();
			break;
		case DELETE :
			request = requestBuilder.delete(requestBody).build();
			break;
		case PUT:
			request = requestBuilder.put(requestBody).build();
			break;
		}
		System.out.println(request);
		Call call = client.newCall(request);
		try {
			Response response = call.execute();
			String text = response.body().string();
			return text;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void sendRequestAsync(String url, JSONObject body,
			List<YZLHeader> headers, HttpMethod method,final Callback callback) {
		OkHttpClient client = new OkHttpClient();
		Builder requestBuilder = new Request.Builder().url(url);
		if(headers != null){
			for (YZLHeader header : headers) {
				requestBuilder.addHeader(header.getName(), header.getValue());
			}
		}
		FormBody.Builder formBuilder = new FormBody.Builder();
		if(body != null){
			Set<Entry<String, Object>> entries =  body.entrySet();
			for (Entry<String, Object> entry : entries) {
				if(entry.getValue()!= null){
					formBuilder.add(entry.getKey(), entry.getValue().toString());
				}
			}
		}
		
		Request request = requestBuilder.method(method.toString(), formBuilder.build()).build();
		
		Call call = client.newCall(request);
		
		call.enqueue(new okhttp3.Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				if(callback != null){
					callback.onResponse(arg1.body().string());
				}
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				if(callback != null){
					callback.onError(arg1.getMessage());
				}
			}
		});
	}


	@Override
	public String fileUpload(String url, File file, JSONObject body,
			List<YZLHeader> headers, HttpMethod method) {
		return null;
	}

	@Override
	public String sendRequestWithXml(String url, String body, HttpMethod method) {
		return null;
	}




}
