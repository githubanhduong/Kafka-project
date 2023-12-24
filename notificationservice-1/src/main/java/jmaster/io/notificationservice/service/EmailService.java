package jmaster.io.notificationservice.service;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;
import jmaster.io.notificationservice.model.MessageDTO;

public interface EmailService {
	void sendEmail(MessageDTO messageDTO);
}

@Service
class EmailServiceImpl implements EmailService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	SpringTemplateEngine springTemplateEngine;
	
	@Value("${spring.mail.username}")
	private String from;
	
	@Override
	@Async
	public void sendEmail(MessageDTO messageDTO) {
		// TODO Auto-generated method stub
		try {
			logger.info("Sending email");
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
			
//			load templates with the contents 
			Context context = new Context();
			context.setVariable("name", messageDTO.getToName());
			context.setVariable("content", messageDTO.getContent());
			String html = springTemplateEngine.process("welcome", context);
			
//			send mail
			helper.setTo(messageDTO.getTo());
			helper.setText(html, true);
			helper.setSubject(messageDTO.getSubject());
			helper.setFrom(from);
			javaMailSender.send(message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error sending message");
		}
		
	}
}


























