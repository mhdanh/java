package com.mhdanh.mytemplate.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestSchedule {

	@Scheduled(fixedDelay = 86400)
	public void perform() {
      //do something every day 86400 s
    }
	
}
