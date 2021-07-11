package com.example.demo.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.GenericResponse;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/register")
	public GenericResponse createUser(@RequestBody CreateUserRequest userRequest)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		return this.userService.save(userRequest);
	}

	@PostMapping("/authenticate")
	public GenericResponse authenticate(@RequestBody AuthRequest userRequest) throws Exception {
		authenticate(userRequest.getUsername(), userRequest.getPassword());
		final UserDetails userDetails = userService.loadUserByUsername(userRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return new GenericResponse(new AuthResponse(token), "SUCCESS", null, -1);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@PreAuthorize("hasAuthority('Administrator')")
	@GetMapping("/sayhello")
	public GenericResponse sayHello() throws JsonProcessingException {
		System.out.println(new ObjectMapper()
				.writeValueAsString(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
		return new GenericResponse("Hello There!", "SUCCESS", null, -1);
	}
}
