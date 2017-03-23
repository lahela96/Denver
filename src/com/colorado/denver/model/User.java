package com.colorado.denver.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "UserDenver")
public class User extends BaseEntity {
	/**
	 * 
	 */

	private static final long serialVersionUID = -960714782698396108L;

	public static final String DENVER_USER = "DenverUser";
	public static final String USER = "user";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String SALT = "salt";
	public static final String ENABLED = "enabled";
	public static final String ROLES = "roles";

	private String username;
	private String password;
	private String salt;
	protected boolean enabled;
	private String passwordConfirm;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Collection<Role> roles;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	@Transient
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	/*
	 * public Collection<GrantedAuthority> getAllAuthorities(BaseEntity<?> scope) {
	 * if (scope != null) {
	 * scope = HibernateGeneralTools.getInitializedEntity(scope);
	 * }
	 * return GenericTools.getSecurityService().getAllAuthorities(this, scope);
	 * }
	 * 
	 * public UserDetails getDetails(){
	 * if(details == null){
	 * details = GenericTools.getSecurityService().g
	 * }
	 * return details;
	 * }
	 */
	@Override
	@Transient
	public String getPrefix() {
		return USER;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

}
