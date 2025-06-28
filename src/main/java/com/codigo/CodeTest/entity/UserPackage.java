package com.codigo.CodeTest.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
public class UserPackage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private UserData userData;

	@ManyToOne
	private PackageData packageData;

	private int remainingCredits;
	private LocalDateTime purchaseDate;
	private LocalDateTime expiryDate;

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expiryDate);
	}

}
