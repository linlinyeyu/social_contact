package com.zheng.api.server.controller;

import com.zheng.oss.common.constant.OssResult;
import com.zheng.oss.common.constant.OssResultConstant;
import com.zheng.oss.upload.Upload;
import com.zheng.oss.upload.bean.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ZhangShuzheng on 2017/5/15.
 */
@Controller
@RequestMapping("/oss")
public class OssController {

	private static Logger _log = LoggerFactory.getLogger(OssController.class);


	@Autowired
	private Upload upload;
	/**
	 * token生成
	 * @return result;
	 * */
	@GetMapping("/token")
	@ResponseBody
	@CrossOrigin(origins = "*", methods = RequestMethod.GET) // 该注解不支持JDK1.7
	public Object token() {
		Token result = upload.generateToken();
		return new OssResult(OssResultConstant.SUCCESS, result);
	}

	@PostMapping("/upload")
	@ResponseBody
	@CrossOrigin(origins = "*", methods = RequestMethod.POST)
	public Object upload(MultipartFile file){
		return upload.uploadOneFile(file);
	}

}
