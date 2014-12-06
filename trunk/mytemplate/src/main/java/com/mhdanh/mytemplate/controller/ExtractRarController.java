package com.mhdanh.mytemplate.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExtractRarController {

	private static final String INPUT_ZIP_FILE = "D:\\extract\\test.zip";
	private static final String OUTPUT_FOLDER = "D:\\extract";

	@RequestMapping("/extract2")
	@ResponseBody
	private void extract2() throws IOException {
		unZipIt(INPUT_ZIP_FILE, OUTPUT_FOLDER);
	}

	public void unZipIt(String zipFile, String outputFolder) {

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
