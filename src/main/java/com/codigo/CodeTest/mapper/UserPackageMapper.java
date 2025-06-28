package com.codigo.CodeTest.mapper;

import org.springframework.stereotype.Component;

import com.codigo.CodeTest.dto.UserPackageDto;
import com.codigo.CodeTest.entity.UserPackage;

@Component
public class UserPackageMapper {

	public UserPackageDto toDto(UserPackage entity) {
		if (entity == null) {
			return null;
		}

		// Map to UserPackageDto
		UserPackageDto dto = new UserPackageDto();
		dto.setId(entity.getId());
		dto.setPackageName(entity.getPackageData().getName());
		dto.setCountry(entity.getPackageData().getCountry());
		dto.setExpiryDate(entity.getExpiryDate());
		dto.setIsExpired(entity.isExpired());
		dto.setRemainingCredits(entity.getRemainingCredits());
		return dto;
	}
}
