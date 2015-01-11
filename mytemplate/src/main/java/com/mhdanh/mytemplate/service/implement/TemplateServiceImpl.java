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
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.mhdanh.mytemplate.dao.TemplateDao;
import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.domain.Template;
import com.mhdanh.mytemplate.service.CategoryService;
import com.mhdanh.mytemplate.service.UnzipService;
import com.mhdanh.mytemplate.service.TemplateService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.UploadTemplateDTO;

@Service
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
	public int uploadTemplate(UploadTemplateDTO uploadTemplate) {
		if (!uploadTemplate.getFileTemplate().isEmpty()) {
			try {
				String titleTemplate = uploadTemplate.getTitleTemplate();
				String fileNameTemplate = uploadTemplate.getFileNameTemplate();
				int categoryTemplateId = uploadTemplate.getCategoryTemplateId();
				MultipartFile fileTemplate = uploadTemplate.getFileTemplate();
				MultipartFile fileThumbnail = uploadTemplate.getFileThumbnail();
				String fileNameThumbnail = uploadTemplate
						.getFileNameThumbnail();
				String costTemplate = uploadTemplate.getCostTemplate();

				Category categoryBeUploadTo = categoryService
						.getById(categoryTemplateId);

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
						+ utility
								.convertTextInDatabaseToNormalText(categoryBeUploadTo
										.getName()) + "/"
						+ utility.getNameWithouExtension(fileNameTemplate);

				File folderTemplate = new File(pathFolderTemplate);
				// Create the file on server
				if (!folderTemplate.exists()) {
					folderTemplate.mkdirs();
				}

				// create path to zip file
				String pathToNewZipFile = utility
						.getValueFromPropertiesSystemFile(FOLDER_ZIP_TEMPLATE)
						+ utility
								.convertTextInDatabaseToNormalText(categoryBeUploadTo
										.getName()) + "/" + fileNameTemplate;
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
					this.update(updateTemplate);
					return updateTemplate.getId();
				}else if(templateDao.getUploadTemplateByCategoryAndFileNameNotOwner(categoryTemplateId,fileNameTemplate) == null){
					//add new template
					Template newTemplate = new Template();
					newTemplate.setTitle(titleTemplate);
					newTemplate.setDateCreated(new Date());
					newTemplate.setFileName(fileNameTemplate);
					newTemplate.setCategory(categoryBeUploadTo);
					String link = LINK_TEMPLATE
							+ utility
									.convertTextInDatabaseToNormalText(categoryBeUploadTo
											.getName()) + "/"
							+ utility.getNameWithouExtension(fileNameTemplate)
							+ "/index.html";
					newTemplate.setLink(link);
					newTemplate.setOwner(utility.getUserLogined());
					newTemplate.setThumbnail(fileAfterHash);
					newTemplate.setCost(costTemplate);
					this.add(newTemplate);
					return newTemplate.getId();
				}
			} catch (Exception e) {
				logger.error("upload file failed", e);
				return -1;
			}
		}
		return -1;
	}

	@Override
	public Object checkkUploadTemplateState(int categoryId, String fileName) {
		String overwriteyourtemplate = "msg.upload-template-file-page.text.overwriteyourtemplate";
		String isusedbyothermember = "msg.upload-template-file-page.text.isusedbyothermember";
		
		Map<String, String> jsonObject = new HashMap<String, String>();
		//check overwriting 
		if(templateDao.getUploadTemplateByCategoryAndFileNameOfOwner(categoryId, fileName) != null){
			jsonObject.put("state","overwriteyourtemplate");
			jsonObject.put("overwriteyourtemplate",utility.getMessage(overwriteyourtemplate));
		//check is use by other member
		}else if(templateDao.getUploadTemplateByCategoryAndFileNameNotOwner(categoryId, fileName) != null){
			jsonObject.put("state","isusedbyothermember");
			jsonObject.put("overwriteyourtemplate",utility.getMessage(isusedbyothermember));
		}else{
			jsonObject.put("state","canuse");
		}
		return jsonObject;
	}

	@Override
	public String templateDetail(Model model, int idTemplate) {
		Template templateById = templateDao.getTemplateById(idTemplate);
		if(templateById == null){
			return "/404";
		}
		model.addAttribute("template", templateById);
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
		return wrongFormatTemplate;
	}
}
