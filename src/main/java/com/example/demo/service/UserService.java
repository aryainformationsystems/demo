package com.example.demo.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.GenericResponse;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Component
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	private static final String USER_CREATED_SUCCESSFULLY = "User Created Successfully";

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public GenericResponse save(CreateUserRequest userRequest)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		User user = new User();
		user.setName(userRequest.getName());
		user.setUsername(userRequest.getUsername());
		user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
		user.setRole("User");
		User createdUser = userRepository.save(user);
		UserDTO userDto = new UserDTO();
		userDto.setName(createdUser.getName());
		userDto.setUsername(createdUser.getUsername());
		userDto.setRole(createdUser.getRole());
		return new GenericResponse(userDto, USER_CREATED_SUCCESSFULLY, null, 1);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		List<GrantedAuthority> authorities = new ArrayList<>();
		System.out.println(user.getRole());
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

}
