package com.zheng.common.base;

import com.zheng.common.exception.ArgumentErrorException;
import com.zheng.common.util.PropertiesFileUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

/**
 * 控制器基类
 * Created by ZhangShuzheng on 2017/2/4.
 */
public abstract class BaseController {

	private final static Logger _log = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 统一异常处理
	 * @param request
	 * @param response
	 * @param exception
	 */
	@ExceptionHandler
	public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		_log.error("统一异常处理：", exception);
		request.setAttribute("ex", exception);

		if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
			request.setAttribute("requestHeader", "ajax");
		}
		// shiro没有权限异常
		if (exception instanceof UnauthorizedException) {
			return "/403.jsp";
		}
		// shiro会话已过期异常
		if (exception instanceof InvalidSessionException) {
			return "/error.jsp";
		}
		return "/error.jsp";
	}

	/**
	 * 返回jsp视图
	 * @param path
	 * @return
	 */
	public static String jsp(String path) {
		return path.concat(".jsp");
	}

	/**
	 * 返回thymeleaf视图
	 * @param path
	 * @return
	 */
	public static String thymeleaf(String path) {
		String folder = PropertiesFileUtil.getInstance().get("app.name");
		return "/".concat(folder).concat(path).concat(".html");
	}

	public String param(String key) {
		return param(null, key);
	}

	public String getAgent(){
		return request().getHeader("User-Agent");
	}

	public String param(Map<String, String> params, String key){
		return params != null ? params.get(key) : request().getParameter(key);
	}

	public int getUserId(){

		Object userId = request().getAttribute("userId");

		if(userId == null){
			return 0;
		}

		try{
			return Integer.parseInt(userId.toString());
		}catch (Exception e){
		}
		return 0;
	}

	public Object getUser(){
		return request().getAttribute("user");
	}

	public String getUuid(){

		Object uuid = request().getAttribute("uuid");

		if(uuid == null){
			return null;
		}

		return uuid .toString();
	}


	//获取request中的参数
	public float paramAsFloat(String key, float defaultValue){
		return this.paramAsFloat(null, key, defaultValue);
	}

	//获取body中的参数
	public float paramAsFloat(Map<String, String> params, String key, float defaultValue) {
		String value = param(params, key);
		if(value == null){
			return defaultValue;
		}
		try{
			return Float.parseFloat(value);
		}catch (Exception e){
			return defaultValue;
		}
	}

	public int paramAsInt(String key, int defaultValue){
		return this.paramAsInt(null, key, defaultValue);
	}

	//获取body中的参数
	public int paramAsInt(Map<String, String> params, String key, int defaultValue) {
		String value = param(params, key);
		if(value == null){
			return defaultValue;
		}
		try{
			return Integer.parseInt(value);
		}catch (Exception e){
			return defaultValue;
		}
	}

	public int paramAsInt(String key) {
		if (param(key) == null) throw new ArgumentErrorException("");

		return paramAsInt(key, -1);
	}

	public long paramAsLong(String key, long defaultValue) {
		String value = param(key);
		if(value == null){
			return defaultValue;
		}
		try{
			return Long.parseLong(value);
		}catch (Exception e){
			return defaultValue;
		}
	}

	public boolean paramAsBoolean(String key, boolean defaultValue) {
		String value = param(key);
		if(value == null){
			return defaultValue;
		}
		try{
			return Boolean.parseBoolean(value);
		}catch (Exception e){
			return defaultValue;
		}
	}

	public Boolean paramAsBoolean(String key, Boolean defaultValue) {
		String value = param(key);
		if(value == null){
			return defaultValue;
		}
		try{
			return Boolean.parseBoolean(value);
		}catch (Exception e){
			return defaultValue;
		}
	}

	//获取body中的参数
	public boolean paramAsBoolean(Map<String, String> params, String key, boolean defaultValue) {
		String value = param(params, key);
		if(value == null){
			return defaultValue;
		}
		try{
			return Boolean.parseBoolean(value);
		}catch (Exception e){
			return defaultValue;
		}
	}

	public String getIp(){
		if(RequestContextHolder.getRequestAttributes() == null){
			return "127.0.0.1";
		}
		HttpServletRequest request = request();

		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}

	public static String getDomain(){
		if(RequestContextHolder.getRequestAttributes() == null){
			return "";
		}

		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

		String domain = request.getScheme()
				+"://"+request.getServerName()
				+":"+request.getServerPort()
				+ request.getContextPath() + "/";
		return domain;
	}



	public String header(String key){
		return request().getHeader(key);
	}

	public Map<String, String> headers(){
		Map<String, String> map = new HashMap<>();
		Enumeration<String> enumeration = request().getHeaderNames();
		while (enumeration.hasMoreElements()){
			String name = enumeration.nextElement();
			String value = request().getHeader(name);
			map.put(name, value);
		}
		return map;
	}

	public HttpServletRequest request(){
		if(RequestContextHolder.getRequestAttributes() == null){
			return null;
		}

		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

		return request;
	}


}
