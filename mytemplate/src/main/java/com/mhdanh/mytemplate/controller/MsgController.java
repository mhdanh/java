package com.mhdanh.mytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhdanh.mytemplate.utility.Utility;

@Controller
public class MsgController {
	
	@Autowired
	private Utility utility;
	
	@RequestMapping(value = "/ajax/get-msg",method = RequestMethod.POST)
	@ResponseBody
	private String getMsg(@RequestParam("key") String key){
		return utility.getMessage(key);
	}
	
	@RequestMapping(value = "/ajax/get-msg-with-param",method = RequestMethod.POST)
	@ResponseBody
	private String getMsgWithParam(@RequestParam("key") String key,@RequestParam("param") Object[] param){
		return utility.getMessage(key,param);
	}
	
}
