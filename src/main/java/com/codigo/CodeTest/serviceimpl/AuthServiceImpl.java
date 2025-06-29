package com.codigo.CodeTest.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codigo.CodeTest.dto.LoginRequest;
import com.codigo.CodeTest.dto.UserDto;
import com.codigo.CodeTest.entity.UserData;
import com.codigo.CodeTest.enums.Role;
import com.codigo.CodeTest.exception.BusinessException;
import com.codigo.CodeTest.exception.InvalidTokenException;
import com.codigo.CodeTest.exception.ResourceNotFoundException;
import com.codigo.CodeTest.mapper.UsersMapper;
import com.codigo.CodeTest.repository.UserRepository;
import com.codigo.CodeTest.service.AuthService;
import com.codigo.CodeTest.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService{
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private EmailServiceImpl emailService;
	@Autowired
	private JwtUtil jwtUtil;

	private final UsersMapper usersMapper;

	public AuthServiceImpl(UsersMapper mapper) {
		this.usersMapper = mapper;
	}

	public String register(UserDto request) {

		// Check User already exists
		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new BusinessException("User with this email is already registered!");
		}

		UserData user = usersMapper.toEntity(request);

		// Set other business fields
		user.setPassword(encoder.encode(request.getPassword()));
		user.setRole(Role.USER);
		user.setVerified(false);
		String token = UUID.randomUUID().toString();
		user.setVerificationToken(token);

		// Save User to DB
		userRepo.save(user);

		// Send Mock Email
		emailService.sendVerifyEmail(request.getEmail(), "Verify your account",
				"Click to verify: http://localhost:8080/api/auth/verify?token=" + token);
		return token;
	}

	public void verify(String token) {

		UserData user = userRepo.findByVerificationToken(token)
				.orElseThrow(() -> new InvalidTokenException("Invalid token"));

		// Set true is isVerified
		user.setVerified(true);
		user.setVerificationToken(null);

		userRepo.save(user);
	}

	public String login(LoginRequest request) {

		UserData user = userRepo.findByEmail(request.getEmail())
				.orElseThrow(() -> new BusinessException("Invalid Credentials"));

		if (!user.isVerified())
			throw new BusinessException("Email not verified");

		if (!encoder.matches(request.getPassword(), user.getPassword()))
			throw new BusinessException("Invalid Credentials");

		return jwtUtil.generateToken(user.getEmail(), user.getId());
	}

	public void resetPasswordRequest(String email) {

		// TODO fix exception
		UserData user = userRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User with this email not found!"));
		String token = UUID.randomUUID().toString();
		user.setResetToken(token);
		userRepo.save(user);

		emailService.sendVerifyEmail(email, "Reset Password", "Click: http://localhost:8080/auth/api/reset?token=" + token);
	}

	public void resetPassword(String token, String newPassword) {
		UserData user = userRepo.findByResetToken(token)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid token"));
		user.setPassword(encoder.encode(newPassword));
		user.setResetToken(null);
		userRepo.save(user);
	}

	@Cacheable(value = "users", key = "'all'")
	public List<UserDto> getAllUsers() {
		List<UserData> users = userRepo.findAll();
		return users.stream().map(usersMapper::toDto).toList();
	}

	public UserDto getUserProfile(long id) {
		Optional<UserData> data = userRepo.findById(id);
		if(data.isEmpty()) {
			throw new ResourceNotFoundException("User not found by ID");
		}
		UserDto userDto = usersMapper.toDto(data.get());
		return userDto;
	}
	
	public Long getCurrentUserId() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {

			User user = (User) authentication.getPrincipal();
			UserData userData = userRepo.findByEmail(user.getUsername())
					.orElseThrow(() -> new ResourceNotFoundException("User not found"));
			return userData.getId();
		}

		return null;
	}
}
