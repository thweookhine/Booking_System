package com.codigo.CodeTest.service;

import org.springframework.stereotype.Service;

@Service
public interface BookingService {

	String bookClass(Long classId, Long userPackageId, Long userId);

	void cancelBooking(Long classId, Long userId);

}
