package com.codigo.CodeTest.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codigo.CodeTest.dto.PackageDto;
import com.codigo.CodeTest.entity.PackageData;
import com.codigo.CodeTest.entity.UserData;
import com.codigo.CodeTest.entity.UserPackage;
import com.codigo.CodeTest.exception.BusinessException;
import com.codigo.CodeTest.exception.ResourceNotFoundException;
import com.codigo.CodeTest.mapper.PackageMapper;
import com.codigo.CodeTest.repository.PackageRepository;
import com.codigo.CodeTest.repository.UserPackageRepo;
import com.codigo.CodeTest.repository.UserRepository;
import com.codigo.CodeTest.service.MockPaymentService;
import com.codigo.CodeTest.service.PackageService;

@Service
public class PackageServiceImpl implements PackageService {

	@Autowired
	private PackageRepository packageRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserPackageRepo userPackageRepo;

	@Autowired
	private MockPaymentService paymentService;

	private final PackageMapper packageMapper;

	public PackageServiceImpl(PackageMapper mapper) {
		this.packageMapper = mapper;
	}

	@Override
	public PackageDto createPackage(PackageDto packageDto) {

		PackageData packageData = packageMapper.toEntity(packageDto);
		packageData.setAvailable(true);
		packageData = packageRepo.save(packageData);
		return packageMapper.toDto(packageData);
	}

	@Override
	public List<PackageDto> getPackagesByCountry(String country) {
		List<PackageData> packageDataList = new ArrayList<>();
		if (country == null || country.isEmpty()) {
			packageDataList = packageRepo.findAllAvailablePackages();
		} else {
			packageDataList = packageRepo.findAllAvailablePackagesByCountry(country);
		}

		return packageDataList.stream().map(packageMapper::toDto).toList();
	}

	@Override
	public void purchasePackage(Long packageId, Long userId) {

		PackageData packageData = packageRepo.findById(packageId)
				.orElseThrow(() -> new ResourceNotFoundException("Package is not found!"));

		UserData userData = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (!packageData.isAvailable()) {
			throw new BusinessException("Package is not available to purchase");
		}
		
		
		// Mock payment charge
        boolean charged = paymentService.chargeUser(userId, packageData.getPrice(), "USD");

		// Create UserPackage and save to db
		UserPackage userPackage = new UserPackage();
		userPackage.setUserData(userData);
		userPackage.setPackageData(packageData);
		userPackage.setRemainingCredits(packageData.getCredits());
		userPackage.setPurchaseDate(LocalDateTime.now());

		LocalDateTime expiryDate = LocalDateTime.now().plusDays(30);
		userPackage.setExpiryDate(expiryDate);

		userPackageRepo.save(userPackage);
	}
	
	

}
