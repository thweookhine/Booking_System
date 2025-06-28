package com.codigo.CodeTest.service;

import java.util.List;

import com.codigo.CodeTest.dto.LoginRequest;
import com.codigo.CodeTest.dto.UserDto;

public interface AuthService {

	public String register(UserDto request);
	public void verify(String token);
	public String login(LoginRequest request);
	public void resetPasswordRequest(String email);
	public void resetPassword(String token, String newPassword);
	public List<UserDto> getAllUsers();
	public UserDto getUserProfile(long id);
	public Long getCurrentUserId();
}
