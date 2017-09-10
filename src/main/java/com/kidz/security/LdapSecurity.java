package com.kidz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
	private CustomLogoutHandler customLogoutHandler;
	
	@Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AccountAuthenticatoinProvider accountAuthenticationProvider;
	
	
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
	
	@Value("${config.security.exptime:1800000}")
	private long EXPIRATION_TIME;
	
	@Value("${config.security.headerstr:Authorization}")
	private String HEADER_STRING;
	
	@Value("${config.security.token.prefix:Bearer}")
	private String TOKEN_PREFIX;
	
	@Value("${config.security.key:ED9X8B78BA5E74B43194FD88E5EBE}")
	private String SECRET;
	
	@Value("${redis.config.host:192.168.1.50}")
	private String  REDIS_HOST;
	
	@Value("${redis.config.port:6379}")
	private int  REDIS_PORT;
	
	
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	
    	TokenAuthenticationService.EXPIRATIONTIME=EXPIRATION_TIME;
    	TokenAuthenticationService.HEADER_STRING=HEADER_STRING;
    	TokenAuthenticationService.TOKEN_PREFIX=TOKEN_PREFIX;
    	TokenAuthenticationService.SECRET=SECRET;
    	TokenAuthenticationService.REDIS_HOST=REDIS_HOST;
    	TokenAuthenticationService.REDIS_PORT=REDIS_PORT;
    	
    	httpSecurity.
    		authorizeRequests()
    		.antMatchers(HttpMethod.GET, "/health").permitAll()
    		.antMatchers(HttpMethod.POST, "/login").permitAll()
    		.antMatchers(HttpMethod.POST, "/getAuthToken").permitAll()
    		.anyRequest().fullyAuthenticated()
    		.and()
            .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),UsernamePasswordAuthenticationFilter.class)
    		.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    		.and()
    		.logout()
    		.addLogoutHandler(customLogoutHandler);
    	
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(accountAuthenticationProvider);
    }

/*    @Override
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
    
    @Bean
	public LdapContextSource getContextSource() throws Exception{
		
    	LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl(LDAP_URL);
		ldapContextSource.setUserDn(MANAGER_DN);
		ldapContextSource.setPassword(MANAGER_PWD);
		
		return ldapContextSource;
		
	}
	
	@Bean
	public LdapTemplate ldapTemplate() throws Exception{
		
		LdapTemplate ldapTemplate = new LdapTemplate(getContextSource());
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(getContextSource());
		
		return ldapTemplate;
	}*/
	
}
