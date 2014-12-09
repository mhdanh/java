package com.mhdanh.mytemplate.service.implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import com.mhdanh.mytemplate.service.UnzipService;

@Service
public class UnzipServiceImpl implements UnzipService{

	@Override
	public void unZip(String zipFile, String outputFolder) {
		byte[] buffer = new byte[1024];
		try {
			// create output directory is not exists
			File folder = new File(outputFolder);
			if (!folder.exists()) {
				folder.mkdir();
			}
			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);
				
				// check is file
				if (isFilePath(newFile.getAbsoluteFile().toString())) {
					//new folder for child file
					new File(newFile.getParent()).mkdirs();

					FileOutputStream fos = new FileOutputStream(newFile);

					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}

					fos.close();
				}
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private boolean isFilePath(String pathFile){
		String extension = FilenameUtils.getExtension(pathFile);
		if(!extension.isEmpty()){
			return true;
		}
		return false;
	}

}
