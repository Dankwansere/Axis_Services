package com.sans.axis.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sans.axis.domain.AxisResponse;
import com.sans.axis.domain.GenericControlList;
import com.sans.axis.domain.User;
import com.sans.axis.service.IUserService;

@RestController
@RequestMapping(value = "/user/")
@CrossOrigin
public class UserController {
	
	@Autowired
	private IUserService userService;
	private User user;
	private AxisResponse axisResponse;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<AxisResponse> getUser(@RequestBody User user) {
		
		this.axisResponse = new AxisResponse();
		
		try {
			this.user = userService.getUser(user.getUserName(), user.getPassWord());
			if(this.user == null) {
				this.axisResponse.setStatus("Invalid");
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);
			} else {
				this.axisResponse.setData(this.user);
				this.axisResponse.setStatus("Valid");
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);
			}
			
		} catch (Exception ex) {
			this.axisResponse.setStatus("Error");
			this.axisResponse.setException(ex.getMessage());
			return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.SERVICE_UNAVAILABLE);
			
		}
		
	}
	
	@RequestMapping(value = "/validate/{username}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, Boolean> validateUserName(@PathVariable(value = "username") String username) {
		
		System.out.println("Received username: " + username);
		
		boolean isUserExist = userService.validateUserName(username);
		
		if(isUserExist) {
			return Collections.singletonMap("success", true);
		}
		else {
			return Collections.singletonMap("success", false);
		}
		
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Map<String, Boolean> create(@RequestBody User user) {	
		
		if (!(this.validateUserName(user.getUserName()).get("success"))) {
			boolean isUserSaved = userService.createUser(user);
			if (isUserSaved) {
				return Collections.singletonMap("success", true);
			} else {
				return Collections.singletonMap("success", false);
			} 
		} else {
			return Collections.singletonMap("success", false);
		}
		
	}
	
	@RequestMapping(value = "/timesheet", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<GenericControlList>> getUserProjects() {
		
		ArrayList<GenericControlList> projectList = userService.getUserProjects();
		
		return new ResponseEntity<ArrayList<GenericControlList>>(projectList, HttpStatus.OK);
		
	}
	

}
