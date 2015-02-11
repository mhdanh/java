package com.mhdanh.mytemplate.service.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mhdanh.mytemplate.domain.Role;
import com.mhdanh.mytemplate.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl extends CommonServiceImpl<Role> implements RoleService{
	
}
