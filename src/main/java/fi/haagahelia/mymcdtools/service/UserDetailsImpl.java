package fi.haagahelia.mymcdtools.service;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import fi.haagahelia.mymcdtools.domain.Role;
import fi.haagahelia.mymcdtools.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -98796876422549277L;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private User user;

	public UserDetailsImpl(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		Set<Role> roles = user.getRoles();
		log.debug("Get user roles");
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		log.debug("Get user password");
		return user.getPasswordHash();
	}

	@Override
	public String getUsername() {
		log.debug("Get user username");
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		log.debug("Get user permission: Is enabled");
		return user.getEnabledUser();
	}

}