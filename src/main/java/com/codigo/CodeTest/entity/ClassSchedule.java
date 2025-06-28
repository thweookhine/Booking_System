package com.codigo.CodeTest.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String country;
	private int requiredCredits;
	private int maxSlots;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@OneToMany(mappedBy = "classSchedule")
	private List<Booking> bookings;

	@OneToMany(mappedBy = "classSchedule")
	private List<Waitlist> waitlists;

	private boolean isEnded;
}
