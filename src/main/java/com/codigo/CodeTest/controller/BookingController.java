package com.codigo.CodeTest.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codigo.CodeTest.dto.ResponseData;
import com.codigo.CodeTest.dto.UserDto;
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
        return ResponseEntity.ok("Booking cancelled");
    }
}
