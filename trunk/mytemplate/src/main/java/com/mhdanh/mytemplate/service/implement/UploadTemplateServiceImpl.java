package com.mhdanh.mytemplate.service.implement;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.domain.UploadTemplate;
import com.mhdanh.mytemplate.service.CategoryService;
import com.mhdanh.mytemplate.service.UnzipService;
import com.mhdanh.mytemplate.service.UploadTemplateService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.UploadTemplateDTO;

@Service
public class UploadTemplateServiceImpl extends
		CommonServiceImpl<UploadTemplate> implements UploadTemplateService {

	private final Logger logger = Logger
			.getLogger(UploadTemplateServiceImpl.class);

	private final String FOLDER_ZIP_TEMPLATE = "system.url.store.template";
	private final String FOLDER_IMAGE = "system.url.store.image";
	private final String LINK_TEMPLATE = "view/layout/";

	@Autowired
	Utility utility;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UnzipService unzipService;

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
	public boolean uploadTemplate(UploadTemplateDTO uploadTemplate) {
		if (!uploadTemplate.getFileTemplate().isEmpty()) {
			try {
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
						newFileNameForThumbnail.toPath());
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

				// save upload template
				UploadTemplate newTemplate = new UploadTemplate();
				newTemplate.setDateCreated(new Date());
				newTemplate.setName(fileNameTemplate);
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
				return true;
			} catch (Exception e) {
				logger.error("upload file failed", e);
				return false;
			}
		} else {
			return false;
		}
	}

}
