package com.codigo.CodeTest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codigo.CodeTest.dto.PackageDto;
import com.codigo.CodeTest.entity.PackageData;

@Service
public interface PackageService {

	PackageDto createPackage(PackageDto packageDto);

	List<PackageDto> getPackagesByCountry(String country);

	void purchasePackage(Long packageId, Long userId);
}
