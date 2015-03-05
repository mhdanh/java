package com.mhdanh.mytemplate.service.implement;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.dao.RechargeDao;
import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Recharge;
import com.mhdanh.mytemplate.domain.Recharge.CardType;
import com.mhdanh.mytemplate.service.RechargeService;
import com.mhdanh.mytemplate.utility.Utility;
import com.mhdanh.mytemplate.viewmodel.RechargeCardModel;
import com.mhdanh.mytemplate.viewmodel.RechargeCardResponseModel;

@Service
@Transactional
public class RechargeServiceImpl extends
		CommonServiceImpl<Recharge> implements RechargeService {
	
	private static final Logger logger = Logger.getLogger(RechargeServiceImpl.class);
	
	@Autowired
	private RechargeDao rechargeDao;
	@Autowired
	private Utility utility;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	BaokimSCard baokimSCard;
	
	@Override
	public String cardPage(Model model) {
		List<Recharge.CardType> cardTypes = Arrays.asList(Recharge.CardType.values());
		model.addAttribute("cardTypes", cardTypes);
		return "/recharge/card-page";
	}

	@Override
	public String cardAdd(Model model,RechargeCardModel rechargeCardModel) {
		try {
			if(rechargeCardModel != null
					&& !rechargeCardModel.getTypeCard().isEmpty()
					&& !rechargeCardModel.getPinCode().isEmpty()
					&& !rechargeCardModel.getSeriNumber().isEmpty()) {
				
				Account userLogined = utility.getUserLogined();
				Integer currentMoney = userLogined.getTotalMoney();
				//check recharge successful
				RechargeCardResponseModel responseRecharge = baokimSCard.rechargeCard(rechargeCardModel.getTypeCard(), rechargeCardModel.getSeriNumber(), rechargeCardModel.getPinCode());
				if("200".equals(responseRecharge.getResponseCode())) {
					Integer moneyRecharge = responseRecharge.getAmount();
					Recharge rechargeCard = new Recharge();
					rechargeCard.setDate(new Date());
					rechargeCard.setMoney(moneyRecharge);
					rechargeCard.setCardType(CardType.valueOf(rechargeCardModel.getTypeCard()));
					rechargeCard.setRecharger(userLogined);
					rechargeCard.setTransactionId(responseRecharge.getTransactionId());
					if(rechargeDao.save(rechargeCard)) {
						//update total money
						Integer totalMoney = currentMoney + moneyRecharge;
						userLogined.setTotalMoney(totalMoney);
						accountDao.update(userLogined);
					}
				}
				model.addAttribute("responseRecharge", responseRecharge);
				logger.warn("error message recharge: " + responseRecharge.getErrorMessage());
				return "/recharge/card-add";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("card add service error",e);
			
		}
		return "/404";
	}
}
