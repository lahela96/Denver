package com.colorado.denver.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.controller.entityController.RoleController;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;
import com.colorado.denver.services.UserService;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.services.persistence.SessionTools;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PopulateDBWithTestData {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PopulateDBWithTestData.class);
	private HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();

	@Before
	public void before() {

		boolean useUpdate = true;
		LOGGER.info("Creating session factory..");
		SessionTools.createSessionFactory(useUpdate);
		LOGGER.info("Done Creating session factory.");

	}

	@Test
	public void populateDatabase() throws IOException {
		UserService.authorizeSystemuser();
		User docent = createUser("Heinrich Docent", "password", UserService.ROLE_DOCENT);
		User tutor1 = createUser("Baum Tutor L2", "password", UserService.ROLE_TUTOR);
		User tutor2 = createUser("Tim Tutor L2", "password", UserService.ROLE_TUTOR);

		User student = createUser("Peter Student C1", "password", UserService.ROLE_STUDENT);
		User student2 = createUser("Klaus Student C2", "password", UserService.ROLE_STUDENT);
		User student3 = createUser("Tom Student C2", "password", UserService.ROLE_STUDENT);
		User student4 = createUser("Karsten Student C3", "password", UserService.ROLE_STUDENT);

		Set<User> usersTutors = new HashSet<User>();
		usersTutors.add(tutor1);
		usersTutors.add(tutor2);

		Set<User> usersStudentsC1 = new HashSet<User>();
		Set<User> usersStudentsC2 = new HashSet<User>();
		Set<User> usersStudentsC3 = new HashSet<User>();

		usersStudentsC1.add(student);
		usersStudentsC2.add(student2);
		usersStudentsC2.add(student3);
		usersStudentsC3.add(student4);

		Course course = createCourse("Course 1");
		Course course2 = createCourse("Course 2");
		Course course3 = createCourse("Course 3");
		Course course4 = createCourse("Course 4");

		student.setCourse(course);
		student.setCourse(course2);
		student.setCourse(course2);
		student.setCourse(course3);

		Lecture lecture = createLecture("Programmieren I");
		Lecture lecture2 = createLecture("Programmieren II");
		Lecture lecture3 = createLecture("Programmieren III");
		Lecture lecture4 = createLecture("Programmieren IV");

		lecture3.setCourse(course3);
		lecture2.setCourse(course2);
		lecture.setCourse(course);
		lecture4.setCourse(course4);

		course.setUsers(usersStudentsC1);
		course2.setUsers(usersStudentsC2);
		course3.setUsers(usersStudentsC3);

		lecture2.setUsers(usersTutors);

		Set<Lecture> lectures = new HashSet<Lecture>();
		lectures.add(lecture);
		lectures.add(lecture2);
		lectures.add(lecture3);

		Exercise exercise = createExercise("Fibonacci");
		Set<Exercise> exercises = new HashSet<Exercise>();
		exercises.add(exercise);

		Solution solution = createSolution("Fibonacci Loesung");
		Set<Solution> solutions = new HashSet<Solution>();
		solutions.add(solution);

		solution.setExercise(exercise);

		// student.setLectures(lectures);
		// student.setExercises(exercises);
		student.setSolutions(solutions);
		student.setCourse(course);

		exercise.setOwner(docent);
		exercise.setLectures(lectures); // setting on both entities gives error: lecture.setExercises(exercises); +
										// exercise.setLectures(lectures);

		course.setLectures(lectures);
		course.setOwner(docent);

		lecture.setExercises(exercises);
		lecture.setOwner(docent);

		hibCtrl.mergeEntity(solution);
		hibCtrl.mergeEntity(student);
		hibCtrl.mergeEntity(student2);
		hibCtrl.mergeEntity(student3);
		hibCtrl.mergeEntity(student4);
		hibCtrl.mergeEntity(docent);
		hibCtrl.mergeEntity(tutor1);
		hibCtrl.mergeEntity(tutor2);
		hibCtrl.mergeEntity(exercise);
		hibCtrl.mergeEntity(course);
		hibCtrl.mergeEntity(course2);
		hibCtrl.mergeEntity(course3);
		hibCtrl.mergeEntity(course4);
		hibCtrl.mergeEntity(lecture);
		hibCtrl.mergeEntity(lecture2);
		hibCtrl.mergeEntity(lecture3);
		hibCtrl.mergeEntity(lecture4);
	}

	private User createUser(String name, String password, String roleName) {
		User usr = new User();
		usr.setUsername(name);
		usr.setPassword(password);
		Set<Role> roles = new HashSet<Role>();
		roles.add(RoleController.getRoleByName(roleName));
		usr.setRoles(roles);
		hibCtrl.addEntity(usr);
		return usr;
	}

	private Course createCourse(String course_name) {
		Course course = new Course();
		course.setTitle(course_name);
		hibCtrl.addEntity(course);
		return course;
	}

	private Exercise createExercise(String title) {
		Exercise exercise = new Exercise();
		exercise.setTitle(title);
		exercise.setAnwswer(89 + "");
		exercise.setInput(11 + "");
		exercise.setInputType("int");
		exercise.setOutputType("int");
		hibCtrl.addEntity(exercise);

		return exercise;
	}

	private Solution createSolution(String title) throws IOException {

		InputStream is = new FileInputStream("fibonacci.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();

		while (line != null) {
			sb.append(line).append("\n");
			line = buf.readLine();
		}
		buf.close();
		String fileAsString = sb.toString();

		Solution solution = new Solution();
		solution.setTitle(title);

		solution.setCode(fileAsString);
		solution.setSubmitted(false);
		solution.setHasBeenModified(false);

		hibCtrl.addEntity(solution);
		return solution;
	}

	private Lecture createLecture(String lecture_name) {
		Lecture lecture = new Lecture();
		lecture.setTitle(lecture_name);
		hibCtrl.addEntity(lecture);
		return lecture;
	}
}
