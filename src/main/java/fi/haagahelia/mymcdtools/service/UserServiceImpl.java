package fi.haagahelia.mymcdtools.service;

import java.util.Random;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fi.haagahelia.mymcdtools.domain.Role;
import fi.haagahelia.mymcdtools.domain.User;
import fi.haagahelia.mymcdtools.repo.RoleRepository;
import fi.haagahelia.mymcdtools.repo.UserRepository;
import fi.haagahelia.mymcdtools.service.reg.UserRegistrationValid;


/*
 * User service witch controls user requests
 * --login
 * --register
 * --activation
 */

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private UserRepository uRepo;

	private RoleRepository rRepo;

	private final String USER_ROLE = "USER";
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	 
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.uRepo = userRepository;
		this.rRepo = roleRepository;
	}

	//login, find user by username
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User currUser = findByUsername(username);
		
		log.debug("Load user: "+username);

		if (currUser == null) {
			log.debug("User cannot be loaded, "+username+" username not found!");
			throw new UsernameNotFoundException("Username not found:"+username);
		}
		return new UserDetailsImpl(currUser);
	}
	//login, find user by email
	public UserDetailsImpl loadUserByEmail(String email) throws EntityNotFoundException{
		User user = findByUsername(email);
		
		log.debug("Load user: "+email);
		if (user == null) {
			log.debug("User cannot be loaded, "+email+" email address not found!");
			throw new EntityNotFoundException(email);
		}

		return new UserDetailsImpl(user);
	}

	@Override
	public User findByEmail(String email) {
		return uRepo.findByEmail(email);
	}

	@Override
	public User findByUsername(String username) {
		return uRepo.findByUsername(username);
	}
	/*register user
		checks email and username if already exist
	 */
	@Override
	public String registerUser(UserRegistrationValid userToRegister) {
		
		log.info("Register new user");
		
		User user = new User();
		log.debug("Set username: "+userToRegister.getUsername());
		user.setUsername(userToRegister.getUsername());
		log.debug("Set email: "+userToRegister.getEmail());
		user.setEmail(userToRegister.getEmail());
		log.debug("Set password: "+userToRegister.getPassword());
		user.setPasswordHash(passwordEncoder.encode(userToRegister.getPassword()));
		log.debug("Set roles");
		Role userRole = rRepo.findByRole(USER_ROLE);
		if (userRole != null) {
			user.getRoles().add(userRole);
		} else {
			user.addRoles(USER_ROLE);
		}
		user.setEnabledUser(false);
		user.setActivation(generateKey());
		
		uRepo.save(user);
		
		if(uRepo.findByUsername(user.getUsername()) != null) {
			
			log.debug("User: "+userToRegister.getUsername()+" has been registered");
			return "ok";
		}
			else return "There was some problem during saving user";
	}

	public String generateKey()
    {
		log.debug("Generate activation key");
		String key = "";
		Random random = new Random();
		char[] word = new char[16]; 
		for (int j = 0; j < word.length; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}
		key = new String(word);
		log.debug("random code: " + key);
		return key;
    }

	@Override
	public String userActivation(String code) {
		User user = uRepo.findByActivation(code);
		log.debug("Activate user");
		if (user == null) {
			log.debug("Activation failed");
		    return "noresult";
		}
		
		user.setEnabledUser(true);
		user.setActivation("");
		uRepo.save(user);
		log.debug("Activation success :"+user);
		return "ok";
	}

	
}