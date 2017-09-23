

package com.kidz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.kidz.model.Role;
import com.kidz.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.kidz.model.Account user = userRepository.findByUsername(username);

        if (user == null) 
            throw new UsernameNotFoundException("User " + username + " not found.");
        
/*
        if (user.getRoles() == null || user.getRoles().isEmpty()) 
            throw new UsernameNotFoundException("User not authorized.");
*/

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles())
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
        

        User userDetails = new User(user.getUsername(),
        		user.getPassword(),true, user.isEnabled(),
                !user.isExpired(),!user.isLocked(), grantedAuthorities);

        return userDetails;
    }

/*    private final static class UserRepositoryUserDetails extends com.kidz.model.Account implements UserDetails {

        private static final long serialVersionUID = 1L;



        private UserRepositoryUserDetails(com.kidz.model.Account user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            for (Role role : getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
            }
            return grantedAuthorities;
        }

        @Override
        public String getUsername() {
            return getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return !isExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
            return !isLocked();
        }

        @Override
        public boolean isEnabled() {
            return isEnabled();
        }

        @Override
        public Set<Role> getRoles() {
            return getRoles();
        }

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}
    }
*/}
