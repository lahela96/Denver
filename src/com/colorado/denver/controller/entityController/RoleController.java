package com.colorado.denver.controller.entityController;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.model.Role;
import com.colorado.denver.services.persistence.SessionTools;

@RestController
public class RoleController extends ObjectOperationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 218249683879368732L;

	public static Role getRoleByName(String roleName) {
		Session session = SessionTools.sessionFactory.getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Role> role = session.createCriteria(Role.class).setComment("getRole '" + roleName + "'")
				.add(Restrictions.eq(Role.ROLE_NAME, roleName).ignoreCase()).list();
		return role.get(0);
	}
}
