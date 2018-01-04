package com.zheng.common.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class YZLHeader {
	private String name ;
	
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public YZLHeader(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public static List<YZLHeader> createHeaders(Map<String, String> map){

		List<YZLHeader> headers = new ArrayList<>();
		if(map != null && map.size() > 0){
			Set<Entry<String, String>> entrySet = map.entrySet();
			for(Entry<String, String> e : entrySet){
				headers.add(new YZLHeader(e.getKey(), e.getValue()));
			}
		}
		return headers;
	}
	
	
}
