package com.codigo.CodeTest.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codigo.CodeTest.dto.UserPackageDto;
import com.codigo.CodeTest.entity.UserPackage;
import com.codigo.CodeTest.mapper.PackageMapper;
import com.codigo.CodeTest.mapper.UserPackageMapper;
import com.codigo.CodeTest.repository.UserPackageRepo;
import com.codigo.CodeTest.service.UserPackageService;

@Service
public class UserPackageServiceImpl implements UserPackageService {

	@Autowired
	private UserPackageRepo userPackageRepo;
	
	private final UserPackageMapper userPackageMapper;

	public UserPackageServiceImpl(UserPackageMapper mapper) {
		this.userPackageMapper = mapper;
	}
	
	@Override
	public List<UserPackageDto> getPurchasedPackages(Long userId) {
		List<UserPackage> packageList = userPackageRepo.findByUserDataId(userId);
		
		return packageList.stream().map(userPackageMapper::toDto).toList();
	}

}
