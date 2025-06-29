package com.codigo.CodeTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codigo.CodeTest.dto.ResponseData;
import com.codigo.CodeTest.service.AuthService;
import com.codigo.CodeTest.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/{classId}")
	@Operation(summary = "Book Class", description = "Book Class")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Booked Class Successfully"),
			@ApiResponse(responseCode = "404", description = "Data Not Found!"),
			@ApiResponse(responseCode = "400", description = "Business Error!")})
	public ResponseEntity<?> bookClass(@PathVariable Long classId, @RequestParam Long userPackageId) {
		Long userId = authService.getCurrentUserId();
		String message = bookingService.bookClass(classId, userPackageId, userId);
		ResponseData<Object> apiResponse = new ResponseData<>(200, message, null);
    	return ResponseEntity.ok(apiResponse);
    }
	
	@PostMapping("/{classId}/cancel")
	@Operation(summary = "Cancel Booking", description = "Cancel Booking")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Canceled Booking Successfully"),
			@ApiResponse(responseCode = "404", description = "Data Not Found!"),
			@ApiResponse(responseCode = "400", description = "Business Error!")})
    public ResponseEntity<?> cancelBooking(@PathVariable Long classId) {
		Long userId = authService.getCurrentUserId();
        bookingService.cancelBooking(classId, userId);
        ResponseData<Object> apiResponse = new ResponseData<>(200, "Booking has been cancelled!", null);
    	return ResponseEntity.ok(apiResponse);
    }
	
	@PostMapping("/schedule/{id}/check-in")
	@Operation(summary = "Checkin Class", description = "Check In Class")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Checked In Class Successfully"),
			@ApiResponse(responseCode = "404", description = "Data Not Found!"),
			@ApiResponse(responseCode = "400", description = "Business Error!")})
	public ResponseEntity<?> checkIn(@PathVariable Long id) {
		Long userId = authService.getCurrentUserId();
		bookingService.checkIn(id, userId);
		ResponseData<Object> apiResponse = new ResponseData<>(200, "Class has been checked in", null);
    	return ResponseEntity.ok(apiResponse);
	}
}
