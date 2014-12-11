package com.mhdanh.mytemplate.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.domain.UploadTemplate;
import com.mhdanh.mytemplate.service.CategoryService;
import com.mhdanh.mytemplate.service.UnzipService;
import com.mhdanh.mytemplate.service.UploadTemplateService;
import com.mhdanh.mytemplate.utility.Utility;

@Controller
public class ManageFileController {

	private final String FOLDER_ZIP_TEMPLATE = "D:/opt/mytemplate/template/";
	private final String LINK_TEMPLATE = "view/layout/";
	private Logger logger = Logger.getLogger(ManageFileController.class);
	
	@Autowired
	Utility utility;
	@Autowired
	UnzipService unzipService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UploadTemplateService uploadTemplateService;
	
	
	@RequestMapping(value = "/upload-template-file-page")
	public String uploadTemplateFilePage(Model model){
		model.addAttribute("categories", categoryService.getAll());
		return "/upload-template-file-page";
	}
	
	@RequestMapping(value = "/ajax/upload-template-file-page", method = RequestMethod.POST)
	@ResponseBody
	public String uploadTemplateFile(
			@RequestParam(value = "name", required = false) String fileName,
			@RequestParam(value = "categoryId") int categoryId,
			@RequestParam("file") MultipartFile file,HttpServletRequest request) {

		if (!file.isEmpty()) {
			try {
				
				Category categoryBeUploadTo = categoryService.getById(categoryId);
				
				
				byte[] bytes = file.getBytes();
				String pathFolderTemplate = utility.getHtmlWebappPath() + utility.convertTextInDatabaseToNormalText(categoryBeUploadTo.getName()) + "/" + utility.getNameWithouExtension(fileName);
				
				File folderTemplate = new File(pathFolderTemplate);
				// Create the file on server
				if(!folderTemplate.exists()){
					folderTemplate.mkdirs();
				}
				
				//create path to zip file
				String pathToNewZipFile = FOLDER_ZIP_TEMPLATE + utility.convertTextInDatabaseToNormalText(categoryBeUploadTo.getName()) + "/" + fileName;
				File zipFile = new File(pathToNewZipFile);
				//create folder zip template if not exist
				if(!zipFile.getParentFile().exists()){
					zipFile.getParentFile().mkdirs();
				}
				
				FileOutputStream fos = new FileOutputStream(zipFile);
				fos.write(bytes);
				fos.close();
				
				//extract zip file to folder template
				unzipService.unZip(pathToNewZipFile, pathFolderTemplate);
				
				//save upload template
				UploadTemplate newTemplate = new UploadTemplate();
				newTemplate.setDateCreated(new Date());
				newTemplate.setName(fileName);
				newTemplate.setCategory(categoryBeUploadTo);
				String link = LINK_TEMPLATE  
						+ utility.convertTextInDatabaseToNormalText(categoryBeUploadTo.getName()) 
						+ "/" + utility.getNameWithouExtension(fileName) + "/index.html";
				newTemplate.setLink(link);
				newTemplate.setOwner(utility.getUserLogined());
				uploadTemplateService.add(newTemplate);
				return "true";
			} catch (Exception e) {
				logger.error("upload file failed",e);
				return "You failed to upload " + fileName + " => " + e.getMessage();
			}
		} else {
			return "The file was empty.";
		}
	}

}
