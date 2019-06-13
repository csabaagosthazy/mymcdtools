package fi.haagahelia.mymcdtools.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.mymcdtools.service.EmailService;
import fi.haagahelia.mymcdtools.service.UserService;


@Controller
public class UserController {
	
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private UserService userService;
    
    private EmailService emailService;

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/")
	public String home(){
		log.debug("Loading index page");
		return "/pages/index";
	}
	
	@GetMapping("/test")
	public String test(){
		log.debug("Loading index page");
		emailService.sendMessage("from@smtp.mailtrap.io", "to@smtp.mailtrap.io", "Test", "test");
		return "/pages/index";
	}

}