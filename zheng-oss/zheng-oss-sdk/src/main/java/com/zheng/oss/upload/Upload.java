package com.zheng.oss.upload;

import com.zheng.common.util.IOUtil;

import com.zheng.common.util.StringUtil;
import com.zheng.oss.common.constant.OssResult;
import com.zheng.oss.common.constant.OssResultConstant;
import com.zheng.oss.upload.bean.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public abstract class Upload {

	protected FileType fileType = FileType.JPG;
	/**
	 * 上传的文件类型
	 * */
	protected UploadFileType uploadFileType = UploadFileType.IMAGE;
	
	public Upload setFileType(UploadFileType fileType){
		this.uploadFileType = fileType;
		return this;
	}

	/**
	 * 拼接图片路径
	 * @param image
	 * @return
	 */
	public static String getImageUrl(Object image){
		if(image == null){
			return "";
		}
		String img = image.toString();
		if(StringUtil.isEmpty(img)){
			return "";
		}
		if(img.startsWith("http")){
			return img;
		}

		img = UploadFactory.getInstance().getPrefix() +
				img ;


		return img;
	}


	

	
	
	/**
	 * 上传的文件夹的路径
	 */
	protected UploadDir dir = UploadDir.IMAGE;
	protected UploadDir file = UploadDir.JSON;
	
	public Upload setUploadDir(UploadDir dir){
		this.dir = dir;
		return this;
	}
	
	
	
	/**
	 * 上传的文件类型
	 * */
	public static enum UploadFileType{
		IMAGE,AUDIO,VIDEO
	}
	
	public static enum UploadDir{
		AVATER("avater"),AUDIO("audio"), IMAGE("image"),JSON("json");
		
		private String dir;
		
		private UploadDir(String dir){
			this.dir = dir;
		}
		
		public String getDir(){
			return this.dir;
		}
	}

	List<Size> thumbSizes = new ArrayList<>();
	
	
	public OssResult download(String destUrl){
		HttpURLConnection httpUrl = null;  
		URL url = null;  
		
		InputStream stream = null;
		try {  
			url = new URL(destUrl);  
			httpUrl = (HttpURLConnection) url.openConnection();  
			httpUrl.connect();  
			stream = httpUrl.getInputStream();
		} catch (Exception e) {
		}
		String[] tt = destUrl.split("\\.");
		
		if(tt.length > 1){
			String ext = tt[tt.length - 1];
			this.fileType = FileType.getTypeFromExt(ext);
		}
		
		FileEntity response = this.upload(stream);
		if(httpUrl != null){
			httpUrl.disconnect();
		}
		return new OssResult(OssResultConstant.SUCCESS,response);
	}
	
	public OssResult uploadOneFile(MultipartFile uploadFiles) throws FileException{
		if(uploadFiles == null || uploadFiles.isEmpty()){
			return new OssResult(OssResultConstant.FAILED);
		}
		
		InputStream stream = null;
		try{
			stream = uploadFiles.getInputStream();
		}catch(Exception e){
		}
		if(stream != null){
			FileEntity fileEntity = this.upload(stream);
			return new OssResult(OssResultConstant.SUCCESS, fileEntity) ;
		}
		return new OssResult(OssResultConstant.FAILED);
		
	}
	

	public FileEntity upload(InputStream stream) {
		if(stream == null ){
			throw new FileException("file can not be empty");
		}
        String filename = null;
		try{
			filename = getFileName(stream);
		}catch (IOException e){

		}finally {
			if (filename == null){
				throw new FileException("file failed");
			}
		}


		FileEntity fileEntity = this.uploadByStream(stream, filename);
        return fileEntity;
	}
	
	private String getFileName(InputStream stream) throws IOException{
		long timespan = System.currentTimeMillis();
		FileType type = FileType.PNG;
		switch(uploadFileType){
			case AUDIO :
				type = FileType.AUDIO;
				break;
			case VIDEO:
				type = FileType.VIDEO;
				break;
			case IMAGE:
			default:
				if(stream.markSupported()){
					
					stream.mark(100);
					ImageInputStream iis = null;
					iis = ImageIO.createImageInputStream(stream);
				    
			        // Find all image readers that recognize the image format
			        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			        if (!iter.hasNext()) {
			            return null;
			        }
			        // Use the first reader
			        ImageReader reader = iter.next();

			        // Close stream
					iis.close();
					type = FileType.getTypeFromType(reader.getFormatName());
					stream.reset();
				}else{
					type = this.fileType;
				}
				
		        break;
		}
		this.fileType = type;
		return  timespan + new Random().nextInt(9999) + "." + type.getExt();
	}
	
	protected boolean transferFile(InputStream stream, File target){
		FileOutputStream fos = null;
        BufferedInputStream bis = null; 
        int BUFFER_SIZE = 1024;  
        
		byte[] buf = new byte[BUFFER_SIZE];  
		int size = 0;  
		try {
			bis = new BufferedInputStream(stream);  
			fos = new FileOutputStream(target);  
			while ((size = bis.read(buf)) != -1) {  
		 		fos.write(buf, 0, size);
			} 
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			IOUtil.close(bis);
			IOUtil.close(fos); 
		}
		return true;
	}
	
	protected File getTmpFile(String filename) throws IOException{
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		File dir = new File(path);
		dir = dir.getParentFile();
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(dir.getAbsolutePath() + "/" + filename);
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
		
	}
	
	
	public List<OssResult> uploadMultiFile(MultipartFile[] files){
		List<OssResult> responses = new ArrayList<>();
		
		if(files != null && files.length > 0){
			for (MultipartFile file : files) {
				responses.add(this.uploadOneFile(file));
			}
		}
		return responses;
	}
	
	public List<ThumbImage> generateThumb(File target, String filename){
		List<ThumbImage> list = new ArrayList<ThumbImage>();
		BufferedImage image = null;
		try {
			image = ImageIO.read(target);
		} catch (IOException e) {
			e.printStackTrace();
			return list;
		}
		int width = image.getWidth();
		int height = image.getHeight();
		
		
		String dir = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		dir = new File(dir).getParent() + this.dir.getDir();
		File dirFile = new File(dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		
		for (Size size : this.thumbSizes) {
			float width_rate = width / (float)size.getWidth();
			float height_rate = height / (float)size.getHeight();
			float rate = width_rate > height_rate ? width_rate : height_rate;
			rate = rate < 1 ? 1 : rate;
			int thumbWidth = (int) (width / rate);
			int thunbHeight = (int) (height / rate);
			System.out.println(this.fileType);
			String thumbName = filename + "_w=" + size.getWidth() + "&h=" + size.getHeight()  ;
			System.out.println(thumbName);
			File thumb = new File( dir + "/" + thumbName );
			
			if(FileType.PNG.equals(this.fileType) || FileType.GIF.equals(this.fileType)){
				BufferedImage to= new BufferedImage(thumbWidth, thunbHeight, BufferedImage.TYPE_INT_RGB); 
	            Graphics2D g2d = to.createGraphics(); 
	            to = g2d.getDeviceConfiguration().createCompatibleImage(thumbWidth, thunbHeight, Transparency.TRANSLUCENT); 
	            g2d.dispose(); 
	            
	            g2d = to.createGraphics(); 
	            Image from = image.getScaledInstance(thumbWidth, thunbHeight, Image.SCALE_AREA_AVERAGING); 
	            g2d.drawImage(from, 0, 0, null);
	            g2d.dispose(); 
	            
	            try {
					ImageIO.write(to, this.fileType.getExt(), thumb);
				} catch (IOException e) {
				}
			}else{
				BufferedImage newImage = new BufferedImage(thumbWidth, thunbHeight, image.getType());
	            Graphics g = newImage.getGraphics();
	            g.drawImage(image, 0, 0, thumbWidth, thunbHeight, null);
	            g.dispose();
	            try {
					ImageIO.write(newImage, this.fileType.getExt(), thumb);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			list.add(new ThumbImage(thumbWidth, thunbHeight, thumb, false));
		}
		return list;
	}



	
	/**
	 * 第一个file为原图，其他为缩略图
	 * */
	public List<ThumbImage> generateThumb(InputStream is,String filename){
		File tmpFile = null;
		List<ThumbImage> list = new ArrayList<ThumbImage>();
		try {
			tmpFile = getTmpFile(filename);
		} catch (IOException e) {
			e.printStackTrace();
			return list;
		}
		if(!this.transferFile(is, tmpFile)){
			return list;
		}
		list.add(new ThumbImage(0, 0, tmpFile, true));
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(tmpFile);
		} catch (FileNotFoundException e) {
			return list;
		}
		
		list.addAll(this.generateThumb(tmpFile, filename));
		IOUtil.close(fis);
		IOUtil.close(is);
		return list;
		
	}

	public abstract Token generateToken();

	public abstract String getSuffix(int width, int height);

	public abstract String getSuffix();
	
	public abstract String getPrefix();
	
	public abstract FileEntity uploadByStream(InputStream stream, String filename);
}
