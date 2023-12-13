package com.incresol.app.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailSenderConfig {
	@Bean
	public JavaMailSender javemailsender() {
		JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("salmanrobin125@gmail.com");
		mailSender.setPassword("kbxhsirknuorjdma");
		mailSender.setJavaMailProperties(getMailProperties());
		return mailSender;
	}
	
    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Trust the Gmail server
        return properties;
    }
}
