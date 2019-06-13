package fi.haagahelia.mymcdtools.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncodeService {
	
	private BCryptPasswordEncoder passwordEncoder;

	public BCryptPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public EncodeService(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	public String encodePassword(String password) {
		
		String encodedPassword = passwordEncoder.encode(password);
		
		return encodedPassword;
	}
	
	

}
