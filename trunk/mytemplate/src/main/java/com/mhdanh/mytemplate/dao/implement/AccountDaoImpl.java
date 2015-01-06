package com.mhdanh.mytemplate.dao.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.domain.Account;

@Transactional
@Repository
public class AccountDaoImpl extends CommonDaoImpl<Account> implements AccountDao{

	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean existUsernameAndPasword(String username, String password) {
		List<Account> existAccounts = sessionFactory.getCurrentSession()
				.createCriteria(Account.class)
				.add(Restrictions.eq("username", username))
				.add(Restrictions.eq("password", password))
				.list();
		if(!existAccounts.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Account getAccountByUsername(String username) {
		return (Account) sessionFactory.getCurrentSession()
				.createCriteria(Account.class)
				.add(Restrictions.eq("username", username))
				.uniqueResult();
	}

	@Override
	public Account getAccountByEmail(String email) {
		return (Account) sessionFactory.getCurrentSession()
				.createCriteria(Account.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
	}

}
