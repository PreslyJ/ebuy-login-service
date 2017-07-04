package com.ctf.sims.login.security;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserAttributesMapper implements AttributesMapper<Object> {

	@Override
	public User mapFromAttributes(Attributes attributes) throws NamingException {
		
		Attribute groups = attributes.get("memberof");
		User userObject = new User();

		System.out.println(attributes.get("memberOf"));
				 
		NamingEnumeration<?> ne=groups.getAll();
		
		while(ne.hasMore()){
			String string=(String) ne.next();
			if(string.toLowerCase().contains("cn=permissions")){
				String parts[] = string.split(",");
				String autho[]=parts[0].split("cn=");
				userObject.getAuthorities().add(new SimpleGrantedAuthority(autho[1].replace(":","")));
			}
			
		}
		
		
		/*Iterator iter= (Iterator) groups.getAll();
		*/
		
		
		
/*		List<String> wordList = Arrays.asList(groups.getAll());  	 
*/
		
/*		for (String string : wordList) {
				
			if(string.toLowerCase().contains("cn=permissions")){
				String parts[] = string.split(",");
				String autho[]=parts[0].split("cn=");
				authorities.add(new SimpleGrantedAuthority(autho[1].replace(":","")));
			}
*/
	      
		//return Collections.unmodifiableList(authorities);
		
		
		
/*		if (attributes.get("") == null){
			System.out.println("Telephone is null for " + commonName);
		}else{
			String telephone = attributes.get("telephoneNumber").get().toString();
			userObject.setTelephone(telephone);
		}*/
		return userObject;
	}

}