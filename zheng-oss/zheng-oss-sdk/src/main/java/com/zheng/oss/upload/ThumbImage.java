package com.zheng.oss.upload;


import com.zheng.oss.upload.bean.Size;

import java.io.File;


public class ThumbImage {
	
	private Size size;
	
	private File file;
	
	private boolean domain;
	
	public boolean isDomain() {
		return domain;
	}

	public void setDomain(boolean domain) {
		this.domain = domain;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Size getSize() {
		return size;
	}

	public ThumbImage(int width, int height , File file, boolean domain) {
		this.size = new Size(width, height);
		this.file = file;
		this.domain = domain;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
}
