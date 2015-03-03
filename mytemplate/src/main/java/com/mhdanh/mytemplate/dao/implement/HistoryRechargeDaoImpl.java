package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.HistoryRechargeDao;
import com.mhdanh.mytemplate.domain.HistoryRecharge;

@Transactional
@Repository
public class HistoryRechargeDaoImpl extends CommonDaoImpl<HistoryRecharge>
		implements HistoryRechargeDao {

}
