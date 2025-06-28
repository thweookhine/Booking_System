package com.codigo.CodeTest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codigo.CodeTest.entity.ClassSchedule;
import com.codigo.CodeTest.entity.UserData;
import com.codigo.CodeTest.entity.Waitlist;

public interface WaitlistRepository extends JpaRepository<Waitlist, Long> {
	// Get the first user in the waitlist for a class (FIFO order)
	Optional<Waitlist> findFirstByClassScheduleOrderByCreatedAtAsc(ClassSchedule classSchedule);

	// Get all waitlist entries for a class
	List<Waitlist> findByClassSchedule(ClassSchedule classSchedule);

	// Check if user already in waitlist for this class
	boolean existsByUserDataAndClassSchedule(UserData userData, ClassSchedule classSchedule);

	// Delete waitlist entry by user and class
	void deleteByUserAndClassSchedule(UserData userData, ClassSchedule classSchedule);
}
