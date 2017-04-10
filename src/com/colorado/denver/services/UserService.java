package com.colorado.denver.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.colorado.denver.controller.entityController.RoleController;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.User;
import com.colorado.denver.services.persistence.SessionTools;
import com.colorado.denver.tools.DenverConstants;

@Service
public class UserService {

	BCryptPasswordEncoder passWordEncoder = new BCryptPasswordEncoder();

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	public static final String ROLE_STUDENT = "ROLE_STUDENT";
	public static final String ROLE_TUTOR = "ROLE_TUTOR";
	public static final String ROLE_DOCENT = "ROLE_DOCENT";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	public static User getCurrentUser() {
		// Find out username and retrieve the user from DB
		// We do NOT use the user object returned by the Token!!!!s
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			LOGGER.error("NO Authentification found!");
			return null;
		} else {

			String name = getLoginNameFromAuthentication(auth);
			LOGGER.info("Getting User from DB: " + name);
			return getUserByLoginName(name);
		}

	}

	public static UsernamePasswordAuthenticationToken authorizeSystemuser() {
		Role role = RoleController.getRoleByName(ROLE_ADMIN);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		return authorizeUserByLoginName(DenverConstants.SYSTEM, "password", roles);
	}

	public static UsernamePasswordAuthenticationToken authorizeUserByLoginName(String username, String password, Collection<Role> roles) {

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, roles);

		SecurityContext secContext = createNewSecurityContext(authToken);
		SecurityContextHolder.setContext(secContext);

		return authToken;
	}

	public static SecurityContext createNewSecurityContext(UsernamePasswordAuthenticationToken auth) {
		SecurityContext context = new SecurityContextImpl();
		context.setAuthentication(auth);
		return context;
	}

	public static User getUserByLoginName(String loginName) {
		LOGGER.info("Trying to get user from DB: " + loginName);
		// we query Database directly. no use of chache!
		List<User> u = null;
		Session session = SessionTools.sessionFactory.openSession();
		session.beginTransaction();
		u = session.createCriteria(User.class).setComment("getUser '" + loginName + "'")
				.add(Restrictions.eq(User.USERNAME, loginName).ignoreCase()).list();
		for (User user : u) {
			LOGGER.info("Sucessfully received user from database: " + user.getUsername());
		}
		session.flush();
		session.close();
		return u.get(0);
	}

	public static String getLoginNameFromAuthentication(Authentication auth) {
		return auth.getPrincipal().toString();
	}

	public void save(User user) {
		LOGGER.info("Security Save for user: " + user.getUsername() + " and password: " + user.getPassword());
		user.setPassword(passWordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>());// TODO: Not
										// clean!

		// Hibernate save for user!
		LOGGER.info("Security Save for user: " + user.getUsername() + "sucessful! newPassword: " + user.getPassword());

	}

}