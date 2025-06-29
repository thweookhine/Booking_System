package com.codigo.CodeTest.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	public boolean sendVerifyEmail(String email, String subject, String body);
}
