package com.colorado.denver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.controller.entityController.RoleController;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.User;
import com.colorado.denver.services.UserService;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.services.persistence.SessionTools;
import com.colorado.denver.tools.DenverConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DenverDBSetupTest {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverDBSetupTest.class);

	@Before
	public void before() {
		// Using hibernate config file!
		boolean useUpdate = false;
		LOGGER.info("Creating session factory..");
		dropAllSequences();
		SessionTools.createSessionFactory(useUpdate);
		LOGGER.info("Done Creating session factory.");

	}

	@Test
	public void setupDatabase() {

		Role roleAdmin = createRole(UserService.ROLE_ADMIN);
		Role roleStudent = createRole(UserService.ROLE_STUDENT);
		Role roleDocent = createRole(UserService.ROLE_DOCENT);
		Role roleTutor = createRole(UserService.ROLE_TUTOR);
		User systemUser = createSystemUser();

		updateRoles(roleAdmin, roleStudent, roleDocent, roleTutor, systemUser);
	}

	@After
	public void after() {
		SessionTools.sessionFactory.close();

	}

	private boolean updateRoles(Role roleAdmin, Role roleStudent, Role roleDocent, Role roleTutor, User systemUser) {
		Set<User> systemUsers = new HashSet<>();
		systemUsers.add(systemUser);
		roleAdmin.setUsers(systemUsers);
		roleStudent.setUsers(systemUsers);
		roleDocent.setUsers(systemUsers);
		roleTutor.setUsers(systemUsers);

		try {
			HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
			hibCtrl.mergeEntity(roleAdmin);
			hibCtrl.mergeEntity(roleStudent);
			hibCtrl.mergeEntity(roleDocent);
			hibCtrl.mergeEntity(roleTutor);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private Role createRole(String name) {
		LOGGER.info(
				"Creating new Role in Database CREATE! If you try to create roles during 'update' YOU'LL FUCK EVERYTHING UP");
		LOGGER.info("Database Update mode not supported. (DIY)");
		Role role = new Role();
		role.setRoleName(name);
		HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
		hibCtrl.addEntity(role);

		return role;
	}

	private static User createSystemUser() { // Creating system user

		User systemUser = new User();
		systemUser.setUsername(DenverConstants.SYSTEM);
		systemUser.setPassword("password");
		LOGGER.info("Created System User");

		// Hib save HibernateController hibCtrl =
		HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();

		Role systemRole = RoleController.getRoleByName(UserService.ROLE_ADMIN);

		LOGGER.info("Got Role:" + systemRole.getRoleName());
		ArrayList<Role> systemRoles = new ArrayList<Role>(0);
		Set<User> systemUsers = new HashSet<User>();
		systemUsers.add(systemUser);
		systemRole.setUsers(systemUsers);

		systemRoles.add(systemRole);
		systemUser.setRoles(systemRoles);

		hibCtrl.addEntity(systemUser);
		hibCtrl.updateEntity(systemRole);
		LOGGER.info("Sucessful systemuser Save(Database)");
		UsernamePasswordAuthenticationToken returnedToken = UserService.authorizeSystemuser();
		User returnedU = UserService.getUserByLoginName(returnedToken.getPrincipal().toString());

		// Make sure authorization worked
		assertNotNull("Authorizitation of system user faield!", returnedU);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		assertNotNull("Authentication is Null!", auth);
		assertEquals(returnedU.getUsername(), UserService.getLoginNameFromAuthentication(auth));
		return returnedU;

	}

	private static void dropAllSequences() {
		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			LOGGER.error("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
		}

		LOGGER.info("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/Denver", "postgres", "password");

		} catch (SQLException e) {

			LOGGER.error("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			try {
				Statement stmt = connection.createStatement();
				String sql = "drop sequence if exists course_sequence;drop sequence if exists exercise_sequence;drop sequence if exists lecture_sequence;drop sequence if exists role_sequence;drop sequence if exists solution_sequence;drop sequence if exists user_sequence;";
				stmt.executeUpdate(sql);
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			LOGGER.error("Failed to make connection!");
		}
	}

}
