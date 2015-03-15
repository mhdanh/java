package com.mhdanh.mytemplate.utility;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Category;
import com.mhdanh.mytemplate.service.CategoryService;

@Component
public class ElFunctions {
	
	private final static Logger logger = Logger.getLogger(ElFunctions.class);
	
	public static List<Category> getCategories(){
		CategoryService categoryService = SpringContext.getApplicationContext().getBean(CategoryService.class);
		return categoryService.getAll();
	}
	
	public static Account getUserLogined(){
		Utility utility = SpringContext.getApplicationContext().getBean(Utility.class);
	    return utility.getUserLogined();
	}
	
	public static String getDateToString(Date date){
		Utility utility = SpringContext.getApplicationContext().getBean(Utility.class);
		try {
			if(date != null){
				String strDate = "";
				Calendar currentDate = Calendar.getInstance();
				int dayAfterSubtract = (int) Math
						.floor((currentDate.getTimeInMillis() - date
								.getTime()) / (1000 * 60 * 60 * 24));

				if (Math.floor(dayAfterSubtract / 7) < 1) {
					if (dayAfterSubtract == 0) {
						strDate = utility.getMessage("msg.layout.today");
					} else if (dayAfterSubtract == 1) {
						strDate = utility.getMessage("msg.layout.yesterday");
					} else {
						strDate = dayAfterSubtract + " " + utility.getMessage("msg.layout.daysago");
					}
				} else if (Math.floor(dayAfterSubtract / 7) >= 1
						&& Math.floor(dayAfterSubtract / 30) < 1) {
					int week = (int) Math.floor(dayAfterSubtract / 7);
					if (week == 1) {
						strDate = utility.getMessage("msg.layout.lastweek");
					} else {
						strDate = week + " " + utility.getMessage("msg.layout.weeksago");
					}
				} else if (Math.floor(dayAfterSubtract / 30) >= 1
						&& Math.floor(dayAfterSubtract / 365) < 1) {
					int month = (int) Math.floor(dayAfterSubtract / 30);
					if (month == 1) {
						strDate = utility.getMessage("msg.layout.lastmonth");
					} else {
						strDate = month + " " + utility.getMessage("msg.layout.monthsago");
					}
				} else if (Math.floor(dayAfterSubtract / 365) >= 1) {
					int year = (int) Math.floor(dayAfterSubtract / 365);
					if (year == 1) {
						strDate = utility.getMessage("msg.layout.lastyear");
					} else {
						strDate = year + " " + utility.getMessage("msg.layout.yearsago");
					}
				}
				return strDate;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error getDateToString");
		}
		return "";
	}
}
