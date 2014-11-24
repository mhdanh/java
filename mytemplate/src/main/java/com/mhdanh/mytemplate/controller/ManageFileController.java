package com.mhdanh.mytemplate.controller;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ManageFileController {

	@RequestMapping(value = "/ajax/upload-file", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				// Create the file on server
				File serverFile = new File("/opt/"+name);
				FileOutputStream fos = new FileOutputStream(serverFile);
				fos.write(bytes);
				fos.close();
				
				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}

}
