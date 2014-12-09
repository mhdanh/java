package com.mhdanh.mytemplate.dao.implement;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mhdanh.mytemplate.dao.RoleDao;
import com.mhdanh.mytemplate.domain.Role;

@Transactional
@Repository
public class RoleDaoImpl extends CommonDaoImpl<Role> implements RoleDao{

}
