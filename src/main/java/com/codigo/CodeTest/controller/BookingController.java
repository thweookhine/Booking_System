package com.codigo.CodeTest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codigo.CodeTest.dto.ClassScheduleDto;
import com.codigo.CodeTest.dto.ResponseData;
import com.codigo.CodeTest.entity.ClassSchedule;
import com.codigo.CodeTest.service.AuthService;
import com.codigo.CodeTest.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/{classId}")
	public ResponseEntity<?> bookClass(@PathVariable Long classId, @RequestParam Long userPackageId) {
		Long userId = authService.getCurrentUserId();
		String message = bookingService.bookClass(classId, userPackageId, userId);
		ResponseData<Object> apiResponse = new ResponseData<>(200, message, null);
    	return ResponseEntity.ok(apiResponse);
    }
	
	@PostMapping("/{classId}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long classId) {
		Long userId = authService.getCurrentUserId();
        bookingService.cancelBooking(classId, userId);
        ResponseData<Object> apiResponse = new ResponseData<>(200, "Booking has been cancelled!", null);
    	return ResponseEntity.ok(apiResponse);
    }
	
	@PostMapping("/schedule/{id}/check-in")
	public ResponseEntity<?> checkIn(@PathVariable Long id) {
		Long userId = authService.getCurrentUserId();
		bookingService.checkIn(id, userId);
		ResponseData<Object> apiResponse = new ResponseData<>(200, "Class has been checked in", null);
    	return ResponseEntity.ok(apiResponse);
	}
}
