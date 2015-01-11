package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.RoleDao;
import com.mhdanh.mytemplate.domain.Role;
import com.mhdanh.mytemplate.domain.Role.ROLE_NAME;

@Transactional
@Repository
public class RoleDaoImpl extends CommonDaoImpl<Role> implements RoleDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Role getRoleByRoleName(ROLE_NAME name) {
		return (Role) sessionFactory.getCurrentSession()
				.createCriteria(Role.class)
				.add(Restrictions.eq("name", name))
				.uniqueResult();
	}

}
