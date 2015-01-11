package com.mhdanh.mytemplate.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;

import com.mhdanh.mytemplate.domain.Account;
import com.mhdanh.mytemplate.domain.Role;
import com.mhdanh.mytemplate.service.AccountService;
import com.mhdanh.mytemplate.utility.Utility;

@Component
public class SecurityServiceImpl implements AuthenticationProvider{

	@Autowired
	AccountService accountService;
	@Autowired
	Utility utility;
	
	@SuppressWarnings("deprecation")
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		
		String username = auth.getPrincipal().toString();
		String password = utility.hashStringWithDefaultKey(auth.getCredentials().toString());
		
		//check password
		if(accountService.existUsernameAndPasword(username, password)){
			List<GrantedAuthority> authorities = new ArrayList<>();
			Account currentAccount = accountService.getAccountByUsername(username);
			List<Role> rolesOfCurrentAccount = currentAccount.getRoles();
			for(Role r:rolesOfCurrentAccount){
				authorities.add(new GrantedAuthorityImpl(r.getName()));
			}
			return new UsernamePasswordAuthenticationToken(username, password, authorities);
		}else{
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	

}
