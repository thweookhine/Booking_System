package com.codigo.CodeTest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codigo.CodeTest.entity.Booking;
import com.codigo.CodeTest.entity.ClassSchedule;
import com.codigo.CodeTest.entity.UserData;
import com.codigo.CodeTest.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	// Count bookings for a specific class (for capacity checking)
	int countByClassSchedule(ClassSchedule classSchedule);

	// Check if user already booked this class
	Optional<Booking> findByUserAndClassSchedule(UserData user, ClassSchedule classSchedule);

	// Check for overlapping bookings
	@Query("""
			SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END
			FROM Booking b
			WHERE b.user.id = :userId
			AND b.classSchedule.startTime < :endTime
			AND b.classSchedule.endTime > :startTime
			AND b.status = 'BOOKED'
			""")
	boolean hasOverlappingClass(Long userId, LocalDateTime startTime, LocalDateTime endTime);
	
    Optional<Booking> findByUserIdAndClassScheduleId(Long userId, Long classScheduleId);
    
    List<Booking> findAllByClassScheduleIdAndStatus(Long classScheduleId, BookingStatus status);

}
