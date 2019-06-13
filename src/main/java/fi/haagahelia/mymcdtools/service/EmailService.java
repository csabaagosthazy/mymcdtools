package fi.haagahelia.mymcdtools.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import fi.haagahelia.mymcdtools.config.EmailConf;


//email service
@Service
public class EmailService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmailConf emailConf;
	
	@Autowired
	private JavaMailSenderImpl mailSender;

	// email send function
	public void sendMessage(String emailFrom, String emailTo, String subject, String content) {
		
		mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConf.getHost());
        mailSender.setPort(emailConf.getPort());
        mailSender.setUsername(emailConf.getUsername());
        mailSender.setPassword(emailConf.getPassword());
        
        log.debug("Get email attributes");
        log.debug("Host: "+emailConf.getHost()+
        		  " Port: "+emailConf.getPort()+
        		  " Username: "+emailConf.getUsername()+
        		  " Password: "+emailConf.getPassword());
		
		SimpleMailMessage email = null;
		
		log.info("Sending email");
		log.debug("Sending email: to: "+emailTo+" subject: "+subject+" content: "+content);
		
		try {
			email = new SimpleMailMessage();
			email.setFrom(emailFrom);
			email.setTo(emailTo);
			email.setSubject(subject);
			email.setText(content);
			mailSender.send(email);
			
			log.debug("Email successfully sent sent");
		} catch (Exception e) {
			log.error("Error sending email: "+
					   LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))+" "+
					   emailTo + " \n " + 
					   "reason: "+e);
		}
		

	}
	
}
