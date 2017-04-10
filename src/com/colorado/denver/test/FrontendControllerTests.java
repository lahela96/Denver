package com.colorado.denver.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.entityController.CourseController;
import com.colorado.denver.controller.entityController.ExerciseController;
import com.colorado.denver.services.UserService;
import com.colorado.denver.services.persistence.SessionTools;
import com.google.gson.GsonBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FrontendControllerTests {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void setUp() throws Exception {
		this.request = new MockHttpServletRequest();
		SessionTools.createSessionFactory(true);// TRUE due to UPDATE!!!
		request.setContentType("application/json");
		this.response = new MockHttpServletResponse();
	}

	@Test
	public void testCourseController() {

		try {
			// Change the file to the call u want to make. CHange Controllers too down below!!!
			InputStream is = new FileInputStream("ultramergetest2.txt");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();

			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}

			String fileAsString = sb.toString();
			UserService.authorizeSystemuser();
			request = new MockHttpServletRequest("POST", "/course");
			request.setContentType("application/json");
			request.addHeader("Accept", "application/json, text/javascript, */*");
			request.setLocalPort(-1);
			request.setContent((fileAsString)
					.getBytes("UTF-8"));

			CourseController mav = new CourseController();
			mav.handleCourseRequest(request, response);

			assertEquals(200, response.getStatus());

			GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
			System.out.println("The content of the response is: " + response.getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testExerciseController() {

		try {
			// Change the file to the call u want to make. Using Exercise entity! ID's are hardcoded!
			InputStream is = new FileInputStream("3.txt");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();

			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}

			String fileAsString = sb.toString();
			UserService.authorizeSystemuser();
			request = new MockHttpServletRequest("POST", "/exercise");
			request.setContentType("application/json");
			request.addHeader("Accept", "application/json, text/javascript, */*");
			request.setLocalPort(-1);
			request.setContent((fileAsString)
					.getBytes("UTF-8"));

			ExerciseController mav = new ExerciseController();
			mav.handleExerciseRequest(request, response);

			assertEquals(200, response.getStatus());

			GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
			System.out.println("The content of the response is: " + response.getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
