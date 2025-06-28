package com.codigo.CodeTest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codigo.CodeTest.dto.UserPackageDto;

@Service
public interface UserPackageService {

	List<UserPackageDto> getPurchasedPackages(Long userId);

}
