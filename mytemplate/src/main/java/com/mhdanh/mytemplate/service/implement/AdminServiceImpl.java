package com.mhdanh.mytemplate.service.implement;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mhdanh.mytemplate.dao.TemplateDao;
import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.domain.Template.TEMPLATE_STATUS;
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
						+ template.getOwner().getUsername()
						+ "/"
						+ utility.convertTextInDatabaseToNormalText(template.getCategory().getName())
						+ "/" + template.getFileName();
				logger.warn("input Template:" + pathZipFileTemplate);
				File fileZipTemplate = new File(pathZipFileTemplate);
				if(fileZipTemplate.exists()){
					//file exist
					String outPutTemplate = utility.getHtmlWebappPath()
							+ template.getOwner().getUsername()
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

	@Override
	public String manageTemplate(Model model) {
		logger.warn("begin manage template");
		try {
			List<Template> newestTemplates = templateDao.getNewestTemplate();
			model.addAttribute("newestTemplates", newestTemplates);
			return "/admin/manage-template";
		} catch (Exception e) {
			System.out.println("error manage template page:" + e);
			logger.error("error manage template page",e);
			return "/error-page";
		}
	}

	@Override
	public Object publishTemplate(int idTemplate) {
		logger.warn("begin publish template");
		Map<String, String> resultJson = new HashMap<String, String>();
		try {
			Template templateById = templateDao.getTemplateById(idTemplate);
			if(!templateById.getStatus().equals(TEMPLATE_STATUS.PUBLISHED)){
				templateById.setStatus(TEMPLATE_STATUS.PUBLISHED);
				templateDao.update(templateById);
			}
			resultJson.put("status", "true");
			resultJson.put("msg",utility.getMessage("msg.admin.manage-temlate.text.unpublish"));
			resultJson.put("statusTemplate",String.valueOf(templateById.getStatus()));
			return resultJson;
		} catch (Exception e) {
			System.out.println("error publish template unsuccessful:" + e);
			logger.error("error publish template unsuccessful:",e);
			resultJson.put("status", "false");
			resultJson.put("msg","error publish template unsuccessful");
			return resultJson;
		}
	}

	@Override
	public Object unPublishTemplate(int idTemplate) {
		logger.warn("begin unpublish template");
		Map<String, String> resultJson = new HashMap<String, String>();
		try {
			Template templateById = templateDao.getTemplateById(idTemplate);
			if(templateById.getStatus().equals(TEMPLATE_STATUS.PUBLISHED)){
				templateById.setStatus(TEMPLATE_STATUS.VIEWED);
				templateDao.update(templateById);
			}
			
			resultJson.put("status", "true");
			resultJson.put("msg",utility.getMessage("msg.admin.manage-temlate.text.publish"));
			resultJson.put("statusTemplate",String.valueOf(templateById.getStatus()));
			return resultJson;
		} catch (Exception e) {
			System.out.println("error unpublish template unsuccessful:" + e);
			logger.error("error unpublish template unsuccessful:",e);
			resultJson.put("status", "false");
			resultJson.put("msg","error unpublish template unsuccessful");
			return resultJson;
		}
	}

	@Override
	public String viewTemplate(int idTemplate) {
		logger.warn("begin view template");
		try {
			Template templateById = templateDao.getTemplateById(idTemplate);
			templateById.setStatus(TEMPLATE_STATUS.VIEWED);
			templateDao.update(templateById);
			return "redirect:/" + templateById.getLink();
		} catch (Exception e) {
			return "/error-page";
		}
	}
}
