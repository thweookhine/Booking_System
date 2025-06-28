package com.codigo.CodeTest.controller;

import java.util.List;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codigo.CodeTest.dto.PackageDto;
import com.codigo.CodeTest.dto.ResponseData;
import com.codigo.CodeTest.dto.UserPackageDto;
import com.codigo.CodeTest.service.AuthService;
import com.codigo.CodeTest.service.PackageService;
import com.codigo.CodeTest.service.UserPackageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/package")
@Tag(name = "Package", description = "APIs for package")
public class PackageController {

	@Autowired
	private PackageService packageService;
	
	@Autowired
	private UserPackageService userPackageService;

	@Autowired
	private AuthService authService;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Package Creation By Admin", description = "Create Package By Admin")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Package created successfully!"),
			@ApiResponse(responseCode = "400", description = "Invalid input data") })
	public ResponseEntity<?> createPackage(@Valid @RequestBody PackageDto req) {
		PackageDto packageDto = packageService.createPackage(req);
		ResponseData<PackageDto> apiResponse = new ResponseData<>(200, "Check Your Email", packageDto);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("getAvailablePackages")
	@Operation(summary = "Get Available Packages", description = "Fetch Available Packages by Country if country is not empty. "
			+ "Fetch all Available Packages if country is empty")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Package fetched successfully!"), })
	public ResponseEntity<?> getAvailablePackages(@RequestParam(required = false) String country) {
		List<PackageDto> list = packageService.getPackagesByCountry(country);
		ResponseData<List<PackageDto>> apiResponse = new ResponseData<>(200, "Packages fetched successfully", list);
		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping("purchasePackage/{packageId}")
	@Operation(summary = "Purchase Package", description = "Purchase package")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Package fetched successfully!"),
			@ApiResponse(responseCode = "400", description = "Business Error"),
			@ApiResponse(responseCode = "404", description = "Resource Not Found!") })
	public ResponseEntity<?> purchasePackage(@PathVariable Long packageId) {
		Long userId = authService.getCurrentUserId();
		packageService.purchasePackage(packageId, userId);
		ResponseData<String> apiResponse = new ResponseData<>(200, "Package is puchased successfully", null);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("getPurchasedPackages")
	public ResponseEntity<?> getPurchasedPackages() {
		Long userId = authService.getCurrentUserId();
		List<UserPackageDto> list = userPackageService.getPurchasedPackages(userId);
		ResponseData<List<UserPackageDto>> apiResponse = new ResponseData<>(200, "Package is puchased successfully", list);
		return ResponseEntity.ok(apiResponse);
	}
}
