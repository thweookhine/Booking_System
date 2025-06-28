package com.codigo.CodeTest.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class MockPaymentService {

	public boolean addPaymentCard(String cardToken, Long userId) {
		// mock card storage
		return true;
	}

	public boolean chargeUser(Long userId, BigDecimal amount, String currency) {
		// mock charge
		return true;
	}
}
