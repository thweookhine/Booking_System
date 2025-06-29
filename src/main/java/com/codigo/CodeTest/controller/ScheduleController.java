package com.codigo.CodeTest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codigo.CodeTest.dto.ClassScheduleDto;
import com.codigo.CodeTest.dto.PackageDto;
import com.codigo.CodeTest.dto.ResponseData;
import com.codigo.CodeTest.entity.ClassSchedule;
import com.codigo.CodeTest.service.ClassScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/schedule")
@Tag(name = "Class Schedule", description = "APIs for schedule")
public class ScheduleController {

	@Autowired
	private ClassScheduleService scheduleService;

	@GetMapping("/getSchedulesByCountry")
	@Operation(summary = "Get Schedules by country", description = "Get Schedules by country")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Schedules by country fetched successfully"),
			@ApiResponse(responseCode = "404", description = "Data Not Found!"),
			@ApiResponse(responseCode = "400", description = "Business Error!")})
	public ResponseEntity<?> getSchedulesByCountry(@RequestParam String country) {
		List<ClassSchedule> schedules = scheduleService.getSchedules(country);
		ResponseData<Object> apiResponse = new ResponseData<>(200, "Class Schedules fetched successfully", schedules);
    	return ResponseEntity.ok(apiResponse);
	}

}
