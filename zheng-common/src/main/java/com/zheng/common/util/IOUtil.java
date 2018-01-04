package com.zheng.common.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class IOUtil {
	private static Random random = new Random();
	
	public static void close(InputStream in){
		if(in != null ){
			try{
				in.close();
			}catch(Exception e){
			}
		}
	}
	
	public static void close(OutputStream out){
		if(out != null ){
			try{
				out.close();
			}catch(Exception e){
			}
		}
	}
	
	public static Random getRandom(){
		if(random == null){
			random = new Random();
		}
		return random;
	}
	
	
	
}
