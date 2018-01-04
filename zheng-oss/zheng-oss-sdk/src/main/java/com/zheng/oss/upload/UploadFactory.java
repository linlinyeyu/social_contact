package com.zheng.oss.upload;


public abstract class UploadFactory {

	public static Upload getInstance() {
		return getInstance(UploadServerType.Qiniu);
	}

	public static Upload getInstance(UploadServerType type) {
		switch(type){
			case Qiniu:
				return new QiniuUpload();
		}
		return new QiniuUpload();

	}
	public static enum UploadServerType {
		Qiniu(QiniuUpload.class);

		private Class<? extends Upload> cls;

		private UploadServerType(Class<? extends Upload> cls) {
			this.cls = cls;
		}

		public Class<? extends Upload> getCls() {
			return this.cls;
		}
	}
	
	public static void main(String[] args) {
	}
}
