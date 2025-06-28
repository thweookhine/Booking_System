package com.codigo.CodeTest.mapper;

import org.springframework.stereotype.Component;

import com.codigo.CodeTest.dto.PackageDto;
import com.codigo.CodeTest.entity.PackageData;

@Component
public class PackageMapper {

	public PackageData toEntity(PackageDto dto) {
		if (dto == null) {
			return null;
		}

		// Map to PackageData
		PackageData packageData = new PackageData();
		packageData.setName(dto.getName());
		packageData.setCountry(dto.getCountry());
		packageData.setCredits(dto.getCredits());
		packageData.setPrice(dto.getPrice());
		packageData.setValidDays(dto.getValidDays());
		packageData.setAvailable(dto.isAvailable());
		return packageData;
	}
	
	public PackageDto toDto(PackageData data) {
		if (data == null) {
			return null;
		}
		
		// Map to PackageDTO
		PackageDto dto = new PackageDto();
		dto.setName(data.getName());
		dto.setCountry(data.getCountry());
		dto.setCredits(data.getCredits());
		dto.setPrice(data.getPrice());
		dto.setValidDays(data.getValidDays());
		dto.setAvailable(data.isAvailable());
		
		return dto;
	}
}
