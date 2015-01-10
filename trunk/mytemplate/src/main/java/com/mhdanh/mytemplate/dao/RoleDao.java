package com.mhdanh.mytemplate.dao;

import com.mhdanh.mytemplate.domain.Role;

public interface RoleDao extends CommonDao<Role>{
	Role getRoleByRoleName(Role.ROLE_NAME name);
}
