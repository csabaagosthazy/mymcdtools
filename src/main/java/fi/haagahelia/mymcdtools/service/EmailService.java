package fi.haagahelia.mymcdtools.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

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
	public boolean sendMessage(String emailFrom, String emailTo, String subject, String content) {
		
		final Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", Boolean.TRUE);
		properties.put("mail.smtp.starttls.enable", Boolean.TRUE);
		properties.put("mail.smtp.quitwait", Boolean.FALSE);
		properties.put("mail.smtp.socketFactory.fallback", Boolean.FALSE);
		properties.put("mail.debug", "true");
		
		mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConf.getHost());
        mailSender.setPort(emailConf.getPort());
        mailSender.setUsername(emailConf.getUsername());
        mailSender.setPassword(emailConf.getPassword());
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setJavaMailProperties(properties);
        
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
			return true;
		} catch (Exception e) {
			log.error("Error sending email: "+
					   LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))+" "+
					   emailTo + " \n " + 
					   "reason: "+e);
			return false;
		}
		

	}
	
}
