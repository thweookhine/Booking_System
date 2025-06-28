package com.codigo.CodeTest.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codigo.CodeTest.dto.LoginRequest;
import com.codigo.CodeTest.dto.ResponseData;
import com.codigo.CodeTest.dto.UserDto;
import com.codigo.CodeTest.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs for user")
public class AuthController {
    @Autowired private AuthService authService;

    @PostMapping("/signup")
	@Operation(summary = "User Signup", description = "Registers a new user with email and password")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User successfully registered"),
			@ApiResponse(responseCode = "400", description = "Invalid input data")})
    public ResponseEntity<?> signup(@Valid @RequestBody UserDto req) {
        String token = authService.register(req);
        ResponseData<String> apiResponse = new ResponseData<>(200, "Check Your Email", "MockToken: "+ token);
    	return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/verify")
    @Operation(summary = "Verify User Email", description = "Verify User Email")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User Email verified Successfully"),
			@ApiResponse(responseCode = "401", description = "Token is Invalid")})
    public ResponseEntity<?> verify(@RequestParam String token) {
        authService.verify(token);
        ResponseData<String> apiResponse = new ResponseData<>(200, "Email has been verified Successfully.!", "");
    	return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Login User")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User logged in Successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid Credentials!")})
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String token = authService.login(req);
        
        ResponseData<Map<String, String>> apiResponse = new ResponseData<>(200, "User logged in Successfully.!", Collections.singletonMap("token", token));
    	return ResponseEntity.ok(apiResponse);
    }
    
    @GetMapping("/getProfile")
    @Operation(summary = "Get User Profile", description = "Get User Profile By ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Get User Profile Successfully"),
			@ApiResponse(responseCode = "404", description = "User Data Not Found!")})
    public ResponseEntity<?> getProfile(@RequestParam long id) {
    	UserDto userDto = authService.getUserProfile(id);
    	ResponseData<UserDto> apiResponse = new ResponseData<>(200, "User logged in Successfully.!", userDto);
    	return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/resetRequest")
    public ResponseEntity<?> resetRequest(@RequestParam String email) {
        authService.resetPasswordRequest(email);
        return ResponseEntity.ok("Reset email sent");
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<?> reset(@RequestParam String token, @RequestParam String newPassword) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password updated");
    }
    
    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseData<List<UserDto>>> getAllUsers() {
    	List<UserDto> users = authService.getAllUsers();
    	ResponseData<List<UserDto>> apiResponse = new ResponseData<>(200, "Users fetched successfully", users);
    	return ResponseEntity.ok(apiResponse);
    }
}
