package com.codigo.CodeTest.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPackageDto {

	private Long id;
	private String packageName;
	private String country;
	private int remainingCredits;
	private LocalDateTime expiryDate;
	private Boolean isExpired;
}
