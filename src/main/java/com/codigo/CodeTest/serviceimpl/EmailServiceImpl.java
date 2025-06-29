package com.codigo.CodeTest.serviceimpl;

import org.springframework.stereotype.Service;

import com.codigo.CodeTest.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	// Mock Email Sender
	public boolean sendVerifyEmail(String email, String subject, String body) {
		return true;
	}
//
//	public void send(String to, String subject, String body) {
////        SimpleMailMessage message = new SimpleMailMessage();
////        message.setTo(to);
////        message.setSubject(subject);
////        message.setText(body);
////        mailSender.send(message);
//		return;
//	}
}
