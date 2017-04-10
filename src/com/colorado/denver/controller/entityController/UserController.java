package com.colorado.denver.controller.entityController;

import java.io.IOException;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.model.Role;
import com.colorado.denver.model.User;
import com.colorado.denver.services.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class UserController extends ObjectOperationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8278059823813912457L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USER, method = RequestMethod.POST)
	@ResponseBody
	public void handleUserRequest(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		UserService.authorizeSystemuser();
		// JSONObject theObject = super.handleRequest(request, response);
		String jsonString = "";
		try {
			jsonString = super.checkRequest(request);
		} catch (JSONException e) {
			LOGGER.error("Error in OOC while handling the request: " + request.toString());
			e.printStackTrace();
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		User entity = gson.fromJson(jsonString, User.class);
		System.out.println("id: " + entity.getId());
		String jsonResponse = super.doCrud(entity, jsonString);
		entity = null; // Let GC run over this quickly
		response.setStatus(200);
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();

	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcome(Model model) {
		return "welcome";
	}

	public static Role calculateRoleBasedOnUser(User usr) {
		// Get the correct role!
		Role role = new Role();
		role.setRoleName("TestRole");// TODO: DELETE THIS!
		return role;
	}
}