package com.mhdanh.mytemplate.service.implement;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mhdanh.mytemplate.domain.HistoryRecharge;
import com.mhdanh.mytemplate.service.HistoryRechargeService;

@Service
@Transactional
public class HistoryRechargeServiceImpl extends
		CommonServiceImpl<HistoryRecharge> implements HistoryRechargeService {

}
