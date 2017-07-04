package com.ctf.sims.login.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Configuration
public class LDAPServiceUtil {
	
	@Autowired
	private LdapTemplate ldapTemplate;


	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
		
		String[] groups = userData.getStringAttributes("memberof");
		 
		List<String> wordList = Arrays.asList(groups);  	 

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (String string : wordList) {
				
			if(string.toLowerCase().contains("cn=permissions")){
				String parts[] = string.split(",");
				String autho[]=parts[0].split("cn=");
				authorities.add(new SimpleGrantedAuthority(autho[1].replace(":","")));
			}

		}
	      
		return Collections.unmodifiableList(authorities);
    } 
 	
 	public Collection<? extends GrantedAuthority> getGrantedAuthorities(String username) {
 		
 		UserAttributesMapper mapper=new UserAttributesMapper();
        
        List<?> users= ldapTemplate.search("uid="+username+",cn=users,cn=accounts,dc=example,dc=test", "(objectClass=person)", mapper);
 		
        User user=(User)users.get(0);
	      
		return user.getAuthorities();
 		
 	}
	
}
