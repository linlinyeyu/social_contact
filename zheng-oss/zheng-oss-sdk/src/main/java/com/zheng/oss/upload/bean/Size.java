package com.zheng.oss.upload.bean;


public class Size {
	private int width;
	
	private int height;
	

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

	public Size(int width, int height) {
		this.width = width;
		this.height = height;
	}

}
