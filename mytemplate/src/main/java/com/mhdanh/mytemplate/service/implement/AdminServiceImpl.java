package com.mhdanh.mytemplate.service.implement;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mhdanh.mytemplate.dao.TemplateDao;
import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.service.AdminService;
import com.mhdanh.mytemplate.service.TemplateService;
import com.mhdanh.mytemplate.service.UnzipService;
import com.mhdanh.mytemplate.utility.Utility;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	
	private static final Logger logger = Logger.getLogger(AdminServiceImpl.class);
	private final String FOLDER_ZIP_TEMPLATE = "system.url.store.template";
	
	
	@Autowired
	private TemplateDao templateDao;
	@Autowired
	private Utility utility;
	@Autowired
	private UnzipService unzipService;
	
	@Override
	public String initAllTemplateFromZipFile() {
		try {
			logger.warn("begin init all template from zip file");
			List<Template> templates = templateDao.getAll();
			for(Template template : templates){
				//get zip file template
				String pathZipFileTemplate = utility.getValueFromPropertiesSystemFile(FOLDER_ZIP_TEMPLATE)
						+ utility.getUserLogined().getUsername()
						+ "/"
						+ utility.convertTextInDatabaseToNormalText(template.getCategory().getName())
						+ "/" + template.getFileName();
				logger.warn("input Template:" + pathZipFileTemplate);
				File fileZipTemplate = new File(pathZipFileTemplate);
				if(fileZipTemplate.exists()){
					//file exist
					String outPutTemplate = utility.getHtmlWebappPath()
							+ utility.getUserLogined().getUsername()
							+ "/"
							+ utility.convertTextInDatabaseToNormalText(template.getCategory().getName())
							+ "/"
							+ utility.getNameWithouExtension(template.getFileName());
					logger.warn("out Template:" + outPutTemplate);
					// extract zip file to folder template
					unzipService.unZip(pathZipFileTemplate, outPutTemplate);
				}else{
					//file note exist
					logger.warn("file not exist:" + pathZipFileTemplate);
				}
			}
			return "Done";
		} catch (Exception e) {
			logger.error("init template unsuccessful:",e);
			return "error";
		}
	}

	@Override
	public String indexAdmin(Model model) {
		logger.warn("index admin");
		return "/admin";
	}
	
}
