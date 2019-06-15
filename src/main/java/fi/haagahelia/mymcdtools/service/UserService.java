package fi.haagahelia.mymcdtools.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import fi.haagahelia.mymcdtools.domain.User;
import fi.haagahelia.mymcdtools.service.reg.UserRegistrationValid;

//Interface for users

public interface UserService extends UserDetailsService {
	
	public String registerUser(UserRegistrationValid userToRegister);
	
	public User findByUsername(String username);

	public User findByEmail(String email);

	public boolean userActivation(String code);
	
}