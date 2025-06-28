package com.codigo.CodeTest.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto implements Serializable  {
	
    private static final long serialVersionUID = 1L;
    
	@NotBlank(message = "Name is required!")
	@NotNull
	private String name;

    @Email(message = "Invalid email format!")
    @NotBlank(message = "Email is required!")
    @NotNull
    private String email;
    
    @NotBlank(message = "Password is required!")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "Country is required!")
    private String country;

}
