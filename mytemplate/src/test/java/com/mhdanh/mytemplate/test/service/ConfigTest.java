package com.mhdanh.mytemplate.test.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/test/test-application-context.xml"})
public class ConfigTest extends AbstractTestNGSpringContextTests{
}
