package com.mhdanh.mytemplate.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhdanh.mytemplate.utility.Utility;

@Controller
public class ViewImgController {
	
	@Autowired
	private Utility utility;
	
	private final String FOLDER_IMAGE = "system.url.store.image";
	
	@RequestMapping("/viewimg/{name:.+}")
	public void viewimg(@PathVariable("name") String name,HttpServletResponse response) throws IOException{
		String pathImg = utility.getValueFromPropertiesSystemFile(FOLDER_IMAGE) + name;
		utility.viewImg(response, pathImg, name);
	}
}
