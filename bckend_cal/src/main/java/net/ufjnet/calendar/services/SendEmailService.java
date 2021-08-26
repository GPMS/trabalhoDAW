package net.ufjnet.calendar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class SendEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void Send(String destination, String subject, String text) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(destination);
		email.setSubject(subject);
		email.setText(text);
		mailSender.send(email);
	}
	
}
