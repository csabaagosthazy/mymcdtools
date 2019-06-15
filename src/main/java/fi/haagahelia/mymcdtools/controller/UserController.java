package fi.haagahelia.mymcdtools.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@RequestMapping("/myschedule")
	public String myschedule(Model model){
		log.debug("Loading myschedule page");
		model.addAttribute("dates", new ArrayList<LocalDate>());
		return "/pages/myschedule";
	}
	
	@PostMapping("/myschedule")
	public String myschedule(@ModelAttribute ArrayList<LocalDate> dates, HttpServletRequest request, Model model){
		log.debug("Loading myschedule post page");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		
		String fromString = request.getParameter("fromdate");
		String toString = request.getParameter("todate");
		
		LocalDate fromDate = null;
		LocalDate toDate = null;
		if(fromString != null && toString != null) {

			fromDate = LocalDate.parse(fromString, formatter);
			toDate = LocalDate.parse(toString, formatter);
			
			log.debug("From date: "+ fromDate.toString());
			log.debug("To date: "+ toDate.toString());

			log.debug("Creating list of dates");
			
			for(LocalDate i = fromDate; i.isBefore(toDate) || i.isEqual(toDate) ; i = i.plusDays(1)) {
				log.debug("Date to list: "+i.toString());
				dates.add(i);
			}
			
		}
		for(int i = 0; i < dates.size(); i++) {
			log.debug("Date list date: "+i+" is "+dates.get(i));
		}
		model.addAttribute("datelist", dates);
		
		return "redirect:/myschedule?list";
	}
	
	
	@GetMapping("/test")
	public String test(){
		log.debug("Loading index page");
		emailService.sendMessage("from@smtp.mailtrap.io", "agosthazy.csaba@gmail.com", "Test", "test");
		return "/pages/index";
	}

}