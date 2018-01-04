package com.zheng.oss.upload.bean;

import java.io.Serializable;

public class FileEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String url;

	private int width;

	private int height;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "FileEntity{" +
				"name='" + name + '\'' +
				", url='" + url + '\'' +
				", width=" + width +
				", height=" + height +
				'}';
	}
}
