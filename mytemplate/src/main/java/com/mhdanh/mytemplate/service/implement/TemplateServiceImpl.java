package com.mhdanh.mytemplate.service.implement;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.dao.CommentTemplateDao;
import com.mhdanh.mytemplate.dao.TemplateDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.domain.CommentTemplate;
import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.domain.Template.TEMPLATE_STATUS;
import com.mhdanh.mytemplate.domain.Template.UNIT_MONEY;
import com.mhdanh.mytemplate.service.CategoryService;
import com.mhdanh.mytemplate.service.UnzipService;
import com.mhdanh.mytemplate.service.TemplateService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.CommentTemplateModel;
import com.mhdanh.mytemplate.viewmodel.LazyLoadTemplateFilterIndex;
import com.mhdanh.mytemplate.viewmodel.UploadTemplateDTO;

@Service
@Transactional
public class TemplateServiceImpl extends
		CommonServiceImpl<Template> implements TemplateService {

	private final Logger logger = Logger
			.getLogger(TemplateServiceImpl.class);

	private final String FOLDER_ZIP_TEMPLATE = "system.url.store.template";
	private final String FOLDER_IMAGE = "system.url.store.image";
	private final String LINK_TEMPLATE = "view/layout/";

	@Autowired
	private Utility utility;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UnzipService unzipService;
	@Autowired
	private TemplateDao templateDao;
	@Autowired
	private CommentTemplateDao commentTemplateDao;
	@Autowired
	private AccountDao accountDao;

	private BufferedImage resizeImage(BufferedImage originalImage, int type) {
		int thumbnailHeight = Integer.valueOf(utility
				.getValueFromPropertiesSystemFile("system.thumbnail.height"));
		int thumbnailWidth = Integer.valueOf(utility
				.getValueFromPropertiesSystemFile("system.thumbnail.width"));

		BufferedImage resizedImage = new BufferedImage(thumbnailWidth,
				thumbnailHeight, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, thumbnailWidth, thumbnailHeight, null);
		g.dispose();
		return resizedImage;
	}

	@Override
	public Object uploadTemplate(UploadTemplateDTO uploadTemplate) {
		if (!uploadTemplate.getFileTemplate().isEmpty()) {
			try {
				String titleTemplate = uploadTemplate.getTitleTemplate();
				String fileNameTemplate = uploadTemplate.getFileNameTemplate();
				int categoryTemplateId = uploadTemplate.getCategoryTemplateId();
				MultipartFile fileTemplate = uploadTemplate.getFileTemplate();
				MultipartFile fileThumbnail = uploadTemplate.getFileThumbnail();
				String fileNameThumbnail = uploadTemplate
						.getFileNameThumbnail();
				int costTemplate = uploadTemplate.getCostTemplate();
				Template.UNIT_MONEY moneyViet = UNIT_MONEY.VND;

				Category categoryBeUploadTo = categoryService
						.getById(categoryTemplateId);

				//check wrong format
				if(!checkTemplateFormat(fileTemplate)){
					Map<String, String> wrongFormatTemplate = new HashMap<String, String>();
					wrongFormatTemplate.put("state","wrongformat");
					String linkDownloadExample = utility.getUrlSystem() + utility.getMessage("msg.upload-template-file-page.text.link.download.template.example");
					String msgWrongFormatTempate = utility.getMessage("msg.upload-template-file-page.text.wrongstructure",linkDownloadExample);
					wrongFormatTemplate.put("message",msgWrongFormatTempate);
					return wrongFormatTemplate;
				} else {
					// save thumbnail
					byte[] bytesThumbnail = fileThumbnail.getBytes();
					File folderImage = new File(
							utility.getValueFromPropertiesSystemFile(FOLDER_IMAGE));
					if (!folderImage.exists()) {
						folderImage.mkdirs();
					}
					String pathThumbnailImage = folderImage + File.separator
							+ fileNameThumbnail;
					FileOutputStream fosImage = new FileOutputStream(new File(
							pathThumbnailImage));
					fosImage.write(bytesThumbnail);
					fosImage.close();
					// resize thumbnail
					String extensionThumbnail = FilenameUtils
							.getExtension(pathThumbnailImage);
					BufferedImage originalImage = ImageIO.read(new File(
							pathThumbnailImage));
					int typeImage = originalImage.getType() == BufferedImage.TYPE_CUSTOM ? BufferedImage.TYPE_INT_ARGB
							: originalImage.getType();
					BufferedImage resizeImage = resizeImage(originalImage,
							typeImage);
					ImageIO.write(resizeImage, extensionThumbnail, new File(
							pathThumbnailImage));
					// hash file thumbnail
					File fileThumbnailNeedChangeName = new File(pathThumbnailImage);
					String fileAfterHash = utility
							.hashFile(fileThumbnailNeedChangeName)
							+ "."
							+ extensionThumbnail;
					String newFileNameAfterHash = folderImage + File.separator
							+ fileAfterHash;
					File newFileNameForThumbnail = new File(newFileNameAfterHash);
					Files.copy(fileThumbnailNeedChangeName.toPath(),
							newFileNameForThumbnail.toPath(),StandardCopyOption.REPLACE_EXISTING);
					// delete old file
					fileThumbnailNeedChangeName.delete();

					byte[] bytesTemplate = fileTemplate.getBytes();
					String pathFolderTemplate = utility.getHtmlWebappPath()
							+ utility.getUserLogined().getUsername()
							+ "/"
							+ utility.convertTextInDatabaseToNormalText(categoryBeUploadTo.getName())
							+ "/"
							+ utility.getNameWithouExtension(fileNameTemplate);

					File folderTemplate = new File(pathFolderTemplate);
					// Create the file on server
					if (!folderTemplate.exists()) {
						folderTemplate.mkdirs();
					}

					// create path to zip file
					String pathToNewZipFile = utility
							.getValueFromPropertiesSystemFile(FOLDER_ZIP_TEMPLATE)
							+ utility.getUserLogined().getUsername() 
							+ "/"
							+ utility.convertTextInDatabaseToNormalText(categoryBeUploadTo.getName())
							+ "/"
							+ fileNameTemplate;
					File zipFile = new File(pathToNewZipFile);
					// create folder zip template if not exist
					if (!zipFile.getParentFile().exists()) {
						zipFile.getParentFile().mkdirs();
					}

					FileOutputStream fos = new FileOutputStream(zipFile);
					fos.write(bytesTemplate);
					fos.close();

					// extract zip file to folder template
					unzipService.unZip(pathToNewZipFile, pathFolderTemplate);

					Template updateTemplate = templateDao.getUploadTemplateByCategoryAndFileNameOfOwner(categoryTemplateId,fileNameTemplate); 
					if(updateTemplate != null){
						//update template
						updateTemplate.setTitle(titleTemplate);
						updateTemplate.setDateModified(new Date());
						updateTemplate.setThumbnail(fileAfterHash);
						updateTemplate.setCost(costTemplate);
						updateTemplate.setSellOff(costTemplate);
						updateTemplate.setStatus(Template.TEMPLATE_STATUS.WAITING);
						updateTemplate.setUnitMoney(moneyViet);
						this.update(updateTemplate);
						
						Map<String, String> jsonObjectSuccessUpload = new HashMap<String, String>();
						jsonObjectSuccessUpload.put("state","uploadsuccess");
						String linkToTemplateDetail = utility.getUrlSystem() + "/template-detail/" + updateTemplate.getId();
						jsonObjectSuccessUpload.put("message", linkToTemplateDetail);
						return jsonObjectSuccessUpload;
					}else {
						//add new template
						Template newTemplate = new Template();
						newTemplate.setTitle(titleTemplate);
						newTemplate.setDateCreated(new Date());
						newTemplate.setDateModified(new Date());
						newTemplate.setFileName(fileNameTemplate);
						newTemplate.setCategory(categoryBeUploadTo);
						newTemplate.setStatus(Template.TEMPLATE_STATUS.WAITING);
						String link = LINK_TEMPLATE
								+ utility.getUserLogined().getUsername()
								+ "/"
								+ utility.convertTextInDatabaseToNormalText(categoryBeUploadTo.getName())
								+ "/"
								+ utility.getNameWithouExtension(fileNameTemplate)
								+ "/index.html";
						newTemplate.setLink(link);
						newTemplate.setOwner(utility.getUserLogined());
						newTemplate.setThumbnail(fileAfterHash);
						newTemplate.setCost(costTemplate);
						newTemplate.setSellOff(costTemplate);
						newTemplate.setUnitMoney(moneyViet);
						newTemplate.setBuy(0);
						
						this.add(newTemplate);
						
						Map<String, String> jsonObjectSuccessUpload = new HashMap<String, String>();
						jsonObjectSuccessUpload.put("state","uploadsuccess");
						String linkToTemplateDetail = utility.getUrlSystem() + "/template-detail/" + newTemplate.getId();
						jsonObjectSuccessUpload.put("message", linkToTemplateDetail);
						return jsonObjectSuccessUpload;
					}
				}
			} catch (Exception e) {
				logger.error("upload file failed", e);
				Map<String, String> jsonObjectError = new HashMap<String, String>();
				jsonObjectError.put("state","error");
				jsonObjectError.put("message","exception unexpected");
				return jsonObjectError;
			}
		}
		Map<String, String> jsonObjectNotChooseFileYet = new HashMap<String, String>();
		jsonObjectNotChooseFileYet.put("state","notchoosefile");
		jsonObjectNotChooseFileYet.put("message","you haven't choose file yet");
		return jsonObjectNotChooseFileYet;
	}

	@Override
	public Object checkkUploadTemplateState(int categoryId, String fileName) {
		String overwriteyourtemplate = "msg.upload-template-file-page.text.overwriteyourtemplate";
		String isusedbyothermember = "msg.upload-template-file-page.text.isusedbyothermember";
		
		Map<String, String> jsonObject = new HashMap<String, String>();
		//check overwriting 
		if (templateDao.getUploadTemplateByCategoryAndFileNameOfOwner(categoryId, fileName) != null){
			jsonObject.put("state","overwriteyourtemplate");
			jsonObject.put("overwriteyourtemplate",utility.getMessage(overwriteyourtemplate));
		} else {
			jsonObject.put("state","canuse");
		}
		return jsonObject;
	}

	@Override
	public String templateDetail(Model model, int idTemplate) {
		Template templateById = templateDao.getTemplateById(idTemplate);
		Account userLogined = utility.getUserLogined();
		if(templateById == null) {
			return "/404";
		}
		boolean ownerTemplate = false;
		if(userLogined != null && templateById.getOwner().getId() == userLogined.getId()){
			ownerTemplate = true;
		}
		
		List<CommentTemplate> parentComments = commentTemplateDao.getCommentsParentByTemplate(templateById);
		
		model.addAttribute("parentComments", parentComments);
		model.addAttribute("template", templateById);
		model.addAttribute("ownerTemplate", ownerTemplate);
		
		return "/template-detail";
	}

	@Override
	public boolean checkTemplateFormat(MultipartFile fileTemplate) {
		try {
			boolean isCorrectFormat = false;
			ZipInputStream zis = new ZipInputStream(fileTemplate.getInputStream());
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				if("index.html".equals(fileName)){
					isCorrectFormat = true;
					break;
				}
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			return isCorrectFormat;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("error read zip file",e);
			return false;
		}
	}

	@Override
	public Object checkFormatAndExistTemplate(UploadTemplateDTO templateUpload) {
		boolean templateFormat = checkTemplateFormat(templateUpload.getFileTemplate());
		if(templateFormat){
			return checkkUploadTemplateState(templateUpload.getCategoryTemplateId(), templateUpload.getFileNameTemplate());
		}
		Map<String, String> wrongFormatTemplate = new HashMap<String, String>();
		wrongFormatTemplate.put("state","wrongformat");
		String linkDownloadExample = utility.getUrlSystem() + utility.getMessage("msg.upload-template-file-page.text.link.download.template.example");
		String msgWrongFormatTempate = utility.getMessage("msg.upload-template-file-page.text.wrongstructure",linkDownloadExample);
		wrongFormatTemplate.put("msg",msgWrongFormatTempate);
		return wrongFormatTemplate;
	}

	@Override
	public List<Template> getAllTemplatePublished() {
		return templateDao.getTemplateByStatus(TEMPLATE_STATUS.PUBLISHED);
	}

	@Override
	public void downloadTemplateFree(int idTemplate,
			HttpServletResponse response) {
		logger.warn("Begin download template id: " + idTemplate);
		Template templateById = templateDao.getTemplateById(idTemplate);
		if(templateById != null){
			if(templateById.getSellOff() == 0){
				try {
					final String folderZipTemplate = "system.url.store.template";
					String pathInputTemplate = utility.getValueFromPropertiesSystemFile(folderZipTemplate)
							+ templateById.getOwner().getUsername()
							+ "/"
							+ utility.convertTextInDatabaseToNormalText(templateById.getCategory().getName())
							+ "/" + templateById.getFileName();
					utility.downloadFile(response,pathInputTemplate, templateById.getFileName());
					//update download time
					Integer plusBuy = templateById.getBuy() + 1;
					templateById.setBuy(plusBuy);
					//update template
					templateDao.update(templateById);
				} catch (IOException e) {
					System.out.println("Download free template unsuccessful:" + e);
					logger.error("Download free template unsuccessful",e);
				}
			}
		}
	}

	@Override
	public List<Template> getLazyTemplatePublished(
			LazyLoadTemplateFilterIndex lazyLoadingCategory) {
		return  templateDao.getLazyTemplatePublished(lazyLoadingCategory);
	}

	@Override
	public int countTotalTemplatePublished() {
		return templateDao.countTemplateByStatus(TEMPLATE_STATUS.PUBLISHED);
	}

	@Override
	public int countTotalTemplatePublishedAndLazyLoadinTemplate(
			LazyLoadTemplateFilterIndex lazyLoadingTemplate) {
		return templateDao.countTotalTemplatePublishedAndLazyLoadinTemplate(lazyLoadingTemplate);
	}

	@Override
	public String myTemplate(Model model) {
		logger.warn("begin my template page");
		try {
			Account userLogined = utility.getUserLogined();
			List<Template> myTemplates = templateDao.getTemplateNewestByOwner(userLogined);
			model.addAttribute("myTemplates", myTemplates);
			return "/template/my-template";
		} catch (Exception e) {
			System.out.println("error init my template unsuccessful: " +e);
			logger.error("error init my template unsuccessful:", e);
			return "/404";
		}
	}

	@Override
	public Object deleteTemplate(int idTemplate) {
		logger.warn("begin delete template");
		Map<String, String> resultJson = new HashMap<>();
		try {
			Template templateById = templateDao.getTemplateById(idTemplate);
			Account userLogined = utility.getUserLogined();
			if(userLogined == null || templateById.getOwner() == null || templateById.getOwner().getId() != userLogined.getId()){
				resultJson.put("state", "false");
				resultJson.put("msg", utility.getMessage("msg.login.text.not.login"));
				return resultJson;
			}
			final String folderZipTemplate = "system.url.store.template";
			String pathInputTemplate = utility.getValueFromPropertiesSystemFile(folderZipTemplate)
					+ templateById.getOwner().getUsername()
					+ "/"
					+ utility.convertTextInDatabaseToNormalText(templateById.getCategory().getName())
					+ "/" + templateById.getFileName();
			if(utility.deleteFile(pathInputTemplate)){
				templateDao.deleteTemplate(templateById);
			}
			resultJson.put("state", "true");
			return resultJson;
		} catch (Exception e) {
			System.out.println("error delete template unsuccessful");
			e.printStackTrace();
			logger.error("error delete template unsuccessful",e);
			resultJson.put("state", "false");
			resultJson.put("msg", utility.getMessage("msg.template.my-temlate.text.delete.unsuccess"));
			return resultJson;
		}
		
	}

	@Override
	public String pageEditDescriptionTemplate(Model model,int idTemplate) {
		try {
			Account userLogined = utility.getUserLogined();
			Template templateById = templateDao.getTemplateById(idTemplate);
			if(templateById == null || userLogined == null || !userLogined.equals(templateById.getOwner())){
				return "/404";
			}
			model.addAttribute("template", templateById);
			return "/template/edit-description-page";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error edit description template page", e);
			return "/404";
		}
		
	}

	@Override
	public String editDescriptionTemplate(int idTemplate, String description) {
		try {
			Account userLogined = utility.getUserLogined();
			Template templateById = templateDao.getTemplateById(idTemplate);
			if(templateById == null || userLogined == null || !userLogined.equals(templateById.getOwner())){
				return "/404";
			}
			templateById.setDescription(description);
			templateDao.update(templateById);
			return "redirect:/template-detail/" + idTemplate;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("edit description unsuccessful ",e);
			return "/404";
		}
	}

	@Override
	public String editLogTemplate(int idTemplate, String log) {
		try {
			Account userLogined = utility.getUserLogined();
			Template templateById = templateDao.getTemplateById(idTemplate);
			if(templateById == null || userLogined == null || !userLogined.equals(templateById.getOwner())){
				return "/404";
			}
			templateById.setLog(log);
			templateDao.update(templateById);
			return "redirect:/template-detail/" + idTemplate;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("edit log unsuccessful ",e);
			return "/404";
		}
	}

	@Override
	public String pageEditLogTemplate(Model model, int idTemplate) {
		try {
			Account userLogined = utility.getUserLogined();
			Template templateById = templateDao.getTemplateById(idTemplate);
			if(templateById == null || userLogined == null || !userLogined.equals(templateById.getOwner())){
				return "/404";
			}
			model.addAttribute("template", templateById);
			return "/template/edit-log-page";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error edit log template page", e);
			return "/404";
		}
	}

	@Override
	public void buyTemplateDirect(int idTemplate, HttpServletResponse response) {
		try {
			Account userLogined = utility.getUserLogined();
			Integer currentMoney = userLogined.getTotalMoney();
			if(userLogined != null) {
				Template templateById = templateDao.getTemplateById(idTemplate);
				if(templateById != null){
					if(templateById.getSellOff() != 0) {
						if(currentMoney >= templateById.getSellOff()) {
							final String folderZipTemplate = "system.url.store.template";
							String pathInputTemplate = utility.getValueFromPropertiesSystemFile(folderZipTemplate)
									+ templateById.getOwner().getUsername()
									+ "/"
									+ utility.convertTextInDatabaseToNormalText(templateById.getCategory().getName())
									+ "/" + templateById.getFileName();
							utility.downloadFile(response,pathInputTemplate, templateById.getFileName());
							//substract money in account
							Integer moneyAfterDownloadTemplate = currentMoney - templateById.getSellOff();
							userLogined.setTotalMoney(moneyAfterDownloadTemplate);
							accountDao.update(userLogined);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error buyTemplateDirect ",e);
		}
		
	}

	@Override
	public String checkBuyTemplateDirect(Model model, int idTemplate) {
		Account userLogined = utility.getUserLogined();
		boolean error = false;
		String strError = "";
		if(userLogined == null) {
			strError = utility.getMessage("msg.login.text.not.login");
			error = true;
		} else {
			Integer currentMoney = userLogined.getTotalMoney();
			Template templateById = templateDao.getTemplateById(idTemplate);
			if(templateById == null) {
				strError = utility.getMessage("msg.template.check.buy.direct.not.exist");
				error = true;
			} else {
				if(templateById.getSellOff() == 0) {
					strError = utility.getMessage("msg.template.check.buy.direct.notpremium");
					error = true;
				} else {
					if(currentMoney < templateById.getSellOff()) {
						strError = utility.getMessage("msg.template.check.buy.direct.not.enough.money");
						error = true;
					}
				}
			}
		}
		if(error) {
			model.addAttribute("error", strError);
			return "/template/check/buy/direct/";
		}
		return "redirect:/template/buy/direct/" + idTemplate;
	}

	@Override
	public Object ajaxCommentTemplate(CommentTemplateModel commentModel) {
		Account userLogined = utility.getUserLogined();
		if(userLogined != null) {
			Template templateById = templateDao.getTemplateById(commentModel.getIdTemplate());
			CommentTemplate parentComment = commentTemplateDao.getCommentTemplateById(commentModel.getIdCommentParent());
			
			CommentTemplate commentTemplate = new CommentTemplate();
			commentTemplate.setDateCreated(new Date());
			commentTemplate.setContent(commentModel.getContentComment());
			commentTemplate.setParentComment(parentComment);
			commentTemplate.setTemplate(templateById);
			commentTemplateDao.save(commentTemplate);
			
		}
		return null;
	}

}
