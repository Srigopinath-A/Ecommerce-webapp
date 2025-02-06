package com.newapp.Webapp.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.newapp.Webapp.Entity.User;
import com.newapp.Webapp.Repo.UserRepo;
import com.newapp.Webapp.exception.NotfoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // this is used for injecting dependency instead of autowiring
public class CustomUserDetailService implements UserDetailsService {
	
	// it is used to load the user name.
	
	
	private final UserRepo userepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userepo.findByEmail(username)
				.orElseThrow(()-> new NotfoundException("user not found"));
		return AuthUser.builder().user(user).build();
	}

}
