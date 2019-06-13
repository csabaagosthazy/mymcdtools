package fi.haagahelia.mymcdtools.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table( name="users" )
public class User {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false )
	private Long id;
	
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String passwordHash;

	private String activation;
	
	private Boolean enabledUser;
	
	private Boolean enabledForum;
	
	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinTable( 
		name = "users_roles", 
		joinColumns = {@JoinColumn(name="user_id")}, 
		inverseJoinColumns = {@JoinColumn(name="role_id")}  
	)
	private Set<Role> roles = new HashSet<Role>();
	
	public User() {}
		

	public User(String username, String email, String passwordHash, String activation, Boolean enabledUser,
			Boolean enabledForum) {
		super();
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
		this.activation = activation;
		this.enabledUser = enabledUser;
		this.enabledForum = enabledForum;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getEnabledUser() {
		return enabledUser;
	}

	public void setEnabledUser(Boolean enabledUser) {
		this.enabledUser = enabledUser;
	}

	public Boolean getEnabledForum() {
		return enabledForum;
	}

	public void setEnabledForum(Boolean enabledForum) {
		this.enabledForum = enabledForum;
	}

	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	public void addRoles(String roleName) {
		if (this.roles == null || this.roles.isEmpty()) 
			this.roles = new HashSet<>();
		this.roles.add(new Role(roleName));
	}


	
}

