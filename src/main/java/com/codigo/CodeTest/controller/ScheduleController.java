package com.codigo.CodeTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codigo.CodeTest.dto.ClassScheduleDto;
import com.codigo.CodeTest.dto.PackageDto;
import com.codigo.CodeTest.dto.ResponseData;
import com.codigo.CodeTest.service.ClassScheduleService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/schedule")
@Tag(name = "Class Schedule", description = "APIs for schedule")
public class ScheduleController {

	@Autowired
	private ClassScheduleService scheduleService;
//	
//	@PostMapping("/createSchedule")
//    @PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity<?> createSchedule(@Valid @RequestBody ClassScheduleDto req) {
//    	scheduleService.createSchedule(req);
//    	ResponseData<Object> apiResponse = new ResponseData<>(200, "Users fetched successfully", null);
//    	return ResponseEntity.ok(apiResponse);
//    }
	
	
}
