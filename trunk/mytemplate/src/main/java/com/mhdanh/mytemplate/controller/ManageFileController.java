package com.mhdanh.mytemplate.controller;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ManageFileController {

	private Logger logger = Logger.getLogger(ManageFileController.class);
	
	@RequestMapping(value = "/upload-template-file-page")
	public String uploadTemplateFilePage(){
		return "manageFileuploadTemplateFilePage";
	}
	
	@RequestMapping(value = "/ajax/upload-template-file-page", method = RequestMethod.POST)
	@ResponseBody
	public String uploadTemplateFile(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				
				byte[] bytes = file.getBytes();
				String pathFolderTemplate = "file:src/main/webapp/WEB-INF/view/html/";
				//String pathFolderTemplate = "/opt/mytemplate/html/";
				File folderTemplate = new File(pathFolderTemplate);
				
				// Create the file on server
				if(!folderTemplate.exists()){
					folderTemplate.mkdirs();
				}
				
				//create path to zip file
				String pathToNewZipFile = pathFolderTemplate + name;
				File rarFile = new File(pathToNewZipFile);
				FileOutputStream fos = new FileOutputStream(rarFile);
				fos.write(bytes);
				fos.close();
				
				return "true";
			} catch (Exception e) {
				logger.error("upload file failed",e);
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "The file was empty.";
		}
	}

}
