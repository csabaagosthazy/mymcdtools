package fi.haagahelia.mymcdtools.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.haagahelia.mymcdtools.config.EmailConf;
import fi.haagahelia.mymcdtools.domain.User;
import fi.haagahelia.mymcdtools.service.EmailService;
import fi.haagahelia.mymcdtools.service.UserServiceImpl;
import fi.haagahelia.mymcdtools.service.reg.UserRegistrationValid;

@Controller
public class RegController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    private UserServiceImpl userServiceImpl;
    
    private EmailService emailService;
    
    private EmailConf emailConf;

    
	@Autowired
	public RegController(UserServiceImpl userServiceImpl, EmailService emailService, EmailConf emailConf) {
		this.userServiceImpl = userServiceImpl;
		this.emailService = emailService;
		this.emailConf = emailConf;
	}
	
    @ModelAttribute("user")
    public UserRegistrationValid userRegistrationValid() {
        return new UserRegistrationValid();
    }
	
	
    @GetMapping("/registration")
	public String registration(Model model){
		log.debug("Loading registration page");
		model.addAttribute("user", new UserRegistrationValid());
		log.debug("User to register: "+model.toString());
		return "auth/registration";
	}
	
//	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	@PostMapping("/reg")
    public String reg(@ModelAttribute("user") @Valid UserRegistrationValid userValid, BindingResult result, HttpServletRequest request) {
		log.info("New user!");

		log.debug("Username: "+userValid.getUsername());
		log.debug("Email: "+userValid.getEmail());
		log.debug("Password: "+userValid.getPassword());
		
		//Check the input data 
		//Is username exist?
		User userToCheck = userServiceImpl.findByEmail(userValid.getEmail());
        if (userToCheck != null){
            result.rejectValue("username", null, "There is already an account registered with this username");
        }
		// Is email exist?
		userToCheck = userServiceImpl.findByEmail(userValid.getEmail());
        if (userToCheck != null){
            result.rejectValue("email", null, "There is already an account registered with this email address");
        }
        //if there are error(s) go back to registration page
        if (result.hasErrors()){
            return "auth/registration";
        }
		
        //if everything is ok, save user
        userServiceImpl.registerUser(userValid);
        //get activation code and set up email message
        String code = "";
        String emailFrom = "";
        String emailTo = "";
        String message = "";
        boolean emailSuccess = false;
        
        
        User userToActivate = userServiceImpl.findByUsername(userValid.getUsername());
        
        if(userToActivate != null) {
        	code = userToActivate.getActivation();
        	emailTo = userToActivate.getEmail();
        	emailFrom = emailConf.getUsername();
        	String url = String.format("%s://%s:%d/activation/",request.getScheme(),  request.getServerName(), request.getServerPort());
        	message = "Dear "+ userToActivate.getUsername()+"\n\n"+
        			"Your registration was successful on MCD tools website\n"+
        			"Please use the activation link below to enable your account!\n\n"+
        			"Activation link: "+ url +code;
        	//sending email and check if it sent successfully
        	emailSuccess = emailService.sendMessage(emailFrom, emailTo, "My MCD tools activiation", message);
        	if(emailSuccess) log.info("Activation email sent successfully");
        	else log.info("Activation email had some problem for user: "+userToActivate.getUsername());
        	
        }else return "redirect:/registration?regerror";
        
        
        if(!emailSuccess) return "redirect:/registration?regsuccess_emailerr";
        else return "redirect:/registration?regsuccess";
    }
	
	@RequestMapping("/activation")
	public String actiVationResult(){
		log.debug("Loading activation result page");
		return "/auth/activation";
	}
	
	 @RequestMapping(path = "/activation/{code}", method = RequestMethod.GET)
	 public String activation(@PathVariable("code") String code, HttpServletResponse response) {
	 boolean result = userServiceImpl.userActivation(code);
	 log.debug("User activation"+result);
	 
	 if(result) return "redirect:/activation?actsuccess";
	 else return "redirect:/activation?acterror";

	 }


}
