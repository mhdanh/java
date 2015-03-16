package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.RechargeDao;
import com.mhdanh.mytemplate.domain.Recharge;

@Transactional
@Repository
public class RechargeDaoImpl extends CommonDaoImpl<Recharge>
		implements RechargeDao {

}
