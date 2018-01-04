package com.zheng.common.http;

import com.alibaba.fastjson.JSONObject;


import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

public class MineHttpClient extends YZLHttpClient{

	@Override
	public String sendRequest(String url, JSONObject body,
			List<YZLHeader> headers, HttpMethod method) {
		StringBuffer result = new StringBuffer(); 
		URL httpUrl = null;
		HttpURLConnection connection = null;
		BufferedReader bufferedReader = null;
		try{
			httpUrl = new URL(url);
			connection = (HttpURLConnection) httpUrl.openConnection();
			connection.setRequestMethod(method.toString());
			if(headers!=null && headers.size()>0){
				for(YZLHeader header : headers){
					connection.setRequestProperty(header.getName(), header.getValue());
				}
			}
			
			if(body!=null && !body.isEmpty()){
				for(Entry<String, Object> key : body.entrySet()){
					connection.setRequestProperty(key.getKey(), body.getString(key.getKey()));
				}
			}
		    connection.connect();
		    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String line;
	        while ((line = bufferedReader.readLine()) != null) {
	            result.append(line);
	        }
	        bufferedReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(bufferedReader != null){
			try {
				bufferedReader.close();
			} catch (IOException e) {
			}
		}
		if(connection != null){
			connection.disconnect();
		}

		return result.toString();
	}

	@Override
	public void sendRequestAsync(String url, JSONObject body,
			List<YZLHeader> headers, HttpMethod method, Callback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String fileUpload(String url, File file, JSONObject body,
			List<YZLHeader> headers, HttpMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
	public String sendRequestWithXml(String url, String body, HttpMethod method) {
        try {

            URL requestUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(method.name());
            conn.setRequestProperty("content-type", "application/xml");
            // 当outputStr不为null时向输出流写数据
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}"+ ce);
        } catch (Exception e) {
            System.out.println("https请求异常：{}"+ e);
        }
        return null;
    }


	public String sendRequestWithJson(String url, JSONObject body, List<YZLHeader> yzlHeader) {
		System.out.println(url);
		try {

			URL requestUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(HttpMethod.POST.name());
			conn.setRequestProperty("content-type", "application/json");
			if(yzlHeader != null && yzlHeader.size() > 0){
				for(YZLHeader header : yzlHeader){
					conn.setRequestProperty(header.getName(), header.getValue());
				}
			}
			// 当outputStr不为null时向输出流写数据
			if (null != body) {

				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(body.toJSONString().getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();

			return buffer.toString();
		} catch (ConnectException ce) {
			System.out.println("连接超时：{}"+ ce);
		} catch (Exception e) {
			System.out.println("https请求异常：{}"+ e);
		}
		return null;
	}
}
