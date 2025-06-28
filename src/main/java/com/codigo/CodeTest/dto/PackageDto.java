package com.codigo.CodeTest.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageDto {

	@NotBlank(message = "Name is required!")
	@NotNull
	private String name;
	
	@NotBlank(message = "Country is required!")
	@NotNull
	private String country;
	
	private int credits;
	
	private BigDecimal price;
	
	private int validDays;

	private boolean isAvailable;
}
