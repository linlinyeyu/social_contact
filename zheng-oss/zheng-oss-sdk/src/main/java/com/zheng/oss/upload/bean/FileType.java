package com.zheng.oss.upload.bean;

public enum FileType {
	JPEG("JPEG","jpg"),JPG("JPEG","jpg"),PNG("PNG","png"),GIF("GIF","gif"),AUDIO("AMR","amr"),AMR("AMR","amr"), VIDEO("WMA","wma");
	
	private String type;
	
	private String ext;
	
	private FileType(String type, String ext){
		this.type = type;
		this.ext = ext;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getExt(){
		return this.ext;
	}
	
	public static FileType getTypeFromExt(String ext){
		if("jpg".equals(ext)){
			return JPEG;
		}else if("gif".equals(ext)){
			return GIF;
		}else {
			return PNG;
		}
	}
	
	public static FileType getTypeFromType(String type){
		if("JPEG".equals(type)){
			return JPEG;
		}else if("JPG".equals(type)){
			return JPG;
		}else if("GIF".equals(type)){
			return GIF;
		}else {
			return PNG;
		}
	}
	
}
