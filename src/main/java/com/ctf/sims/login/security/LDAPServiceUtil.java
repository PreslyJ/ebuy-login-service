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

	//private LdapTemplate ldapTemplate;

/*	@SuppressWarnings("unchecked")
	public Set getAllUsers(){

		UserAttributesMapper mapper = new UserAttributesMapper();
		return new HashSet<>(ldapTemplate.search("ou=users,ou=system", "(objectClass=person)", mapper));
	}
*/
	
	/* public static void main(String[] args) {
	        LdapContextSource lcs = new LdapContextSource();
	        lcs.setUrl("ldap://192.168.1.50:389");
	        lcs.setUserDn("uid=admin,cn=users,cn=accounts,dc=example,dc=test");
	        lcs.setPassword("admin123");
	        lcs.setDirObjectFactory(DefaultDirObjectFactory.class);
	        lcs.afterPropertiesSet();
	        LdapTemplate ldap = new LdapTemplate(lcs);
	        ldap.lookup("uid=admin,cn=users,cn=accounts,dc=example,dc=test");
	        System.out.println(	        ldap.lookup("uid=admin,cn=users,cn=accounts,dc=example,dc=test"));

	        UserAttributesMapper mapper=new UserAttributesMapper();
	        
	        ldapTemplate.search("uid=admin,cn=users,cn=accounts,dc=example,dc=test", "(objectClass=person)", mapper);
	    }*/
	 
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
	        
	        List users= ldapTemplate.search("uid="+username+",cn=users,cn=accounts,dc=example,dc=test", "(objectClass=person)", mapper);
	 		
	        User user=(User)users.get(0);
		      
			return user.getAuthorities();
	 		
	 	}
	
}
