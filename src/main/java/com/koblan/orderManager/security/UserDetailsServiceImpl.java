package com.koblan.orderManager.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.koblan.orderManager.models.User;
import com.koblan.orderManager.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	//UserDetailsServiceImpl override loadUserByUsername method, that
	//will find a record from users database tables to build a UserDetails object for authentication
	
	@Autowired
    UserRepository userRepository;
	
	@Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    	
        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user==null) throw new UsernameNotFoundException("Username " + username+ " not found");
        return UserPrinciple.build(user);
    }

}
