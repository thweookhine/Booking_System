package com.codigo.CodeTest.entity;

import java.time.LocalDateTime;

import com.codigo.CodeTest.enums.BookingStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private UserData userData;
	
	@ManyToOne
	private ClassSchedule classSchedule;
	
	@ManyToOne
    private UserPackage userPackage;
	
	private LocalDateTime bookingTime;

	 @Enumerated(EnumType.STRING)
	private BookingStatus status;

}
