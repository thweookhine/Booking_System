package com.codigo.CodeTest.serviceimpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codigo.CodeTest.entity.Booking;
import com.codigo.CodeTest.entity.ClassSchedule;
import com.codigo.CodeTest.entity.UserData;
import com.codigo.CodeTest.entity.UserPackage;
import com.codigo.CodeTest.entity.Waitlist;
import com.codigo.CodeTest.enums.BookingStatus;
import com.codigo.CodeTest.exception.BusinessException;
import com.codigo.CodeTest.exception.ResourceNotFoundException;
import com.codigo.CodeTest.repository.BookingRepository;
import com.codigo.CodeTest.repository.ClassScheduleRepository;
import com.codigo.CodeTest.repository.UserPackageRepo;
import com.codigo.CodeTest.repository.UserRepository;
import com.codigo.CodeTest.repository.WaitlistRepository;
import com.codigo.CodeTest.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClassScheduleRepository scheduleRepo;

	@Autowired
	private UserPackageRepo userPackageRepo;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private WaitlistRepository waitlistRepository;

	private final RedisTemplate<String, Object> redisTemplate;

	public BookingServiceImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	@Transactional
	public String bookClass(Long classId, Long userPackageId, Long userId) {
		UserData userData = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found!"));

		ClassSchedule schedule = scheduleRepo.findById(classId)
				.orElseThrow(() -> new ResourceNotFoundException("Schedule not found!"));

		UserPackage userPackage = userPackageRepo.findById(userPackageId)
				.orElseThrow(() -> new ResourceNotFoundException("UserPackage not found!"));

		validateBooking(schedule, userPackage, userData);

		// Caching lock key
		String cacheKey = "class_lock:" + classId;
		Boolean lock = redisTemplate.opsForValue().setIfAbsent(cacheKey, "LOCK", Duration.ofSeconds(3));
		if (lock == null || !lock) {
			throw new BusinessException("Class is currently being booked. Please try again.");
		}

		try {
			int bookedCount = bookingRepository.countByClassSchedule(schedule);
			if (bookedCount >= schedule.getMaxSlots()) {
				addToWaitlist(classId, userId);
				return "Class Schedule has reached maximum slots!";
			}

			if (userPackage.getRemainingCredits() < schedule.getRequiredCredits()) {
				throw new BusinessException("Not enough credits");
			}

			Booking booking = new Booking();
			booking.setUserData(userData);
			booking.setClassSchedule(schedule);
			booking.setUserPackage(userPackage);
			booking.setBookingTime(LocalDateTime.now());
			booking.setStatus(BookingStatus.BOOKED);
			bookingRepository.save(booking);

			// Deduct credit
			userPackage.setRemainingCredits(userPackage.getRemainingCredits() - schedule.getRequiredCredits());
			userPackageRepo.save(userPackage);

		} finally {
			redisTemplate.delete(cacheKey);
		}

		return "Class has been booked successfully!";
	}

	public void addToWaitlist(Long classId, Long userId) {
		UserData userData = userRepository.findById(userId).orElseThrow(() -> new BusinessException("User Not Found"));
		ClassSchedule classSchedule = scheduleRepo.findById(classId)
				.orElseThrow(() -> new BusinessException("Class Schedule Not Found"));

		Waitlist waitlist = new Waitlist();
		waitlist.setUserData(userData);
		waitlist.setClassSchedule(classSchedule);
		waitlist.setAddedAt(LocalDateTime.now());
		waitlistRepository.save(waitlist);
	}

	private void validateBooking(ClassSchedule schedule, UserPackage userPackage, UserData user) {
		if (!schedule.getCountry().equals(userPackage.getPackageData().getCountry()))
			throw new BusinessException("Package doesn't match country");
		if (userPackage.getExpiryDate().isBefore(LocalDateTime.now()))
			throw new BusinessException("Package expired");

		// Check for overlapping booking
		boolean hasOverlap = bookingRepository.hasOverlappingClass(user.getId(), schedule.getStartTime(),
				schedule.getEndTime());
		if (hasOverlap)
			throw new BusinessException("Overlapping class booking");
	}

	@Override
	@Transactional
	public void cancelBooking(Long classId, Long userId) {
		UserData userData = userRepository.findById(userId).orElseThrow(() -> new BusinessException("User Not Found"));
		ClassSchedule classSchedule = scheduleRepo.findById(classId)
				.orElseThrow(() -> new BusinessException("Schedule Class Not Found"));

		Booking booking = bookingRepository.findByUserAndClassSchedule(userData, classSchedule)
				.orElseThrow(() -> new BusinessException("No booking found"));

		LocalDateTime now = LocalDateTime.now();
		if (classSchedule.getStartTime().minusHours(4).isBefore(now)) {
			booking.setStatus(BookingStatus.CANCELLED);
		} else {
			UserPackage userPackage = booking.getUserPackage();
			userPackage.setRemainingCredits(userPackage.getRemainingCredits() + classSchedule.getRequiredCredits());
			userPackageRepo.save(userPackage);
			booking.setStatus(BookingStatus.CANCELLED);
		}
		bookingRepository.save(booking);

		Optional<Waitlist> nextInLine = waitlistRepository.findFirstByClassScheduleOrderByCreatedAtAsc(classSchedule);
		if (nextInLine.isPresent()) {
			bookFromWaitlist(nextInLine.get());
		}
	}

	private void bookFromWaitlist(Waitlist waitlist) {
		UserData userData = waitlist.getUserData();
		ClassSchedule classSchedule = waitlist.getClassSchedule();
		Optional<UserPackage> userPackageOpt = userPackageRepo.findAvailablePackageForUser(userData.getId(),
				classSchedule.getCountry(), LocalDateTime.now());

		if (userPackageOpt.isEmpty()
				|| userPackageOpt.get().getRemainingCredits() < classSchedule.getRequiredCredits()) {
			// can't book, remove from waitlist
			waitlistRepository.delete(waitlist);
			return;
		}

		UserPackage userPackage = userPackageOpt.get();
		Booking booking = new Booking(user, classSchedule, userPackage, BookingStatus.BOOKED);
		bookingRepository.save(booking);

		userPackage.setRemainingCredits(userPackage.getRemainingCredits() - classSchedule.getRequiredCredits());
		packageRepository.save(userPackage);

		waitlistRepository.delete(waitlist);
	}

}
