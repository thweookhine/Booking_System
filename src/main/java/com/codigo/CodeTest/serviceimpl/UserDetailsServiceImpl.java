package com.codigo.CodeTest.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codigo.CodeTest.entity.UserData;
import com.codigo.CodeTest.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserData user = userRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new User(
				user.getEmail(),
				user.getPassword(),
				user.isVerified(),
				true, true, true,
				List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole().name()))
				);
	}

}
