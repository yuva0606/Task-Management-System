package com.yuva.app.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.yuva.app.utils.MailBody;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}

	public void sendSimpleMessage(MailBody mailbody) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailbody.to());
		message.setFrom("tyuvashankar@gmail.com");
		message.setSubject(mailbody.subject());
		message.setText(mailbody.text());

		mailSender.send(message);
	}

}
