package com.mhdanh.mytemplate.test.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

public class ExpTest extends ConfigTest{

	@Test
	public void test() {
		Path pathFolderTemplate = Paths.get("D:/opt/mytemplate/html");
		File folderTemplate = new File("/opt/mytemplate/html/");
		// Create the file on server
		if(!folderTemplate.exists()){
			System.out.println("ok nhe");
			folderTemplate.mkdirs();
		}
	}

}
