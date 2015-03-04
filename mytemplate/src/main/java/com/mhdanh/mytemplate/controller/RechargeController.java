package com.mhdanh.mytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mhdanh.mytemplate.service.RechargeService;
import com.mhdanh.mytemplate.viewmodel.RechargeCardModel;

@Controller
public class RechargeController {
	
	@Autowired
	private RechargeService rechargeService;
	
	@RequestMapping("/recharge/card-page")
	private String cardPage(Model model) {
		return rechargeService.cardPage(model);
	}
	
	@RequestMapping(value = "/recharge/card-add", method = RequestMethod.POST)
	private String cardAdd(@ModelAttribute("frmRecharge") RechargeCardModel rechargeCard) {
		return rechargeService.cardAdd(rechargeCard);
	}
	
}
