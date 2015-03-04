package com.mhdanh.mytemplate.service;

import org.springframework.ui.Model;

import com.mhdanh.mytemplate.domain.Recharge;
import com.mhdanh.mytemplate.viewmodel.RechargeCardModel;

public interface RechargeService extends CommonService<Recharge>{

	String cardPage(Model model);

	String cardAdd(Model model, RechargeCardModel rechargeCard);
	
}
