package com.ctf.sims.login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

@Component
public class CustomLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
	
		String[] groups = userData.getStringAttributes("memberof");
		 
		List<String> wordList = Arrays.asList(groups);  	 

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (String string : wordList) {
				
			if(string.toLowerCase().contains("cn=permissions")){
				String parts[] = string.split(",");
				String autho[]=parts[0].split("cn=");
				System.out.println(autho[1]);	
				authorities.add(new SimpleGrantedAuthority(autho[1]));
			}

		}
	      
		return authorities;
    } 

}