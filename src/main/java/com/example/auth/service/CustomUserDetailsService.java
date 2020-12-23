package com.example.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user=userRepository.findByUsername(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Username or PAssword is wrong"));
		new AccountStatusUserDetailsChecker().check(user.get());
		
		return user.get();
		
	}

}
