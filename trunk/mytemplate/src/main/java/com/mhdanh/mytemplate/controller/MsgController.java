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
	Utility utility;
	
	@RequestMapping(value = "/ajax/get-msg",method = RequestMethod.POST)
	@ResponseBody
	private String getMsg(@RequestParam("key") String key){
		String msg = utility.getMessage(key);
		return msg;
	}
	
	@RequestMapping(value = "/ajax/get-msg-with-param",method = RequestMethod.POST)
	@ResponseBody
	private String getMsgWithParam(@RequestParam("key") String key,@RequestParam("param") Object[] param){
		String msg = utility.getMessage(key,param);
		return msg;
	}
	
}
