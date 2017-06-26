package com.ctf.sims.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class LdapSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomLdapAuthoritiesPopulator customLdapAuthoritiesPopulator;
	
	@Autowired
	AuthFilter authFilter;
	
	
	@Value("${ldap.config.url:ldap://192.168.1.50:389}")
	private String LDAP_URL;

	@Value("${ldap.config.managerdn:uid=admin,cn=users,cn=accounts,dc=example,dc=test}")
	private String MANAGER_DN;
	
	@Value("${ldap.config.managerpwd:admin123}")
	private String MANAGER_PWD;

	@Value("${ldap.config.basedn:cn=users,cn=accounts,dc=example,dc=test}")
	private String SEARCH_BASE;
	
	@Value("${config.security.status:0}")
	private int IS_SECURITY_ON;
	
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

    //	httpSecurity.addFilterBefore(authFilter,BasicAuthenticationFilter.class);
    	
    	if(IS_SECURITY_ON==0){
	        httpSecurity.
	        //httpBasic().and().
	        	authorizeRequests()
	        		.antMatchers("/login").permitAll()
	        		.anyRequest().permitAll()
	        		.and()
	        		.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),UsernamePasswordAuthenticationFilter.class)
	        		 .addFilterBefore(new JWTAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
	        		.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	}
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    	authenticationManagerBuilder
			.ldapAuthentication().contextSource().url(LDAP_URL)
			.managerDn(MANAGER_DN)
	         .managerPassword(MANAGER_PWD)
				.and()
					.userSearchBase(SEARCH_BASE)
					.userSearchFilter("uid={0}")
					.ldapAuthoritiesPopulator(customLdapAuthoritiesPopulator);			  
    }
    
}
