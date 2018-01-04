package com.zheng.common.http;

public interface Callback {
	
	void onResponse(String response);
	
	void onError(String error);
	
}
