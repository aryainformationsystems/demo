package com.example.demo.dto;

public class AuthResponse {
	private String authToken;

	public AuthResponse(String authToken) {
		super();
		this.authToken = authToken;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	
}
