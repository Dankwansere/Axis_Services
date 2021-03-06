package com.sans.axis.controller;

import java.util.ArrayList;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sans.axis.domain.AxisResponse;
import com.sans.axis.domain.Employee;
import com.sans.axis.domain.GenericControlList;
import com.sans.axis.domain.User;
import com.sans.axis.domain.UserSingleInfoValidator;
import com.sans.axis.service.IEmployeeService;
import com.sans.axis.service.IUserService;
import com.sans.axis.commons.AxisException;
import com.sans.axis.commons.AxisResponseCodes;

@RestController
@RequestMapping(value = "/user/")
@CrossOrigin
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	private User user;
	private AxisResponse axisResponse;
	private AxisException axisException;
	
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<AxisResponse> getUser(@RequestBody User user) {
		
		this.axisResponse = new AxisResponse();
		
		try {
			this.user = userService.getUser(user.getUsername(), user.getPassword());
			if(this.user == null) {
				this.axisResponse.setStatus(AxisResponseCodes.STATUS_INVALID);
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);
			} else {
				this.axisResponse.setData(this.user);
				this.axisResponse.setStatus(AxisResponseCodes.STATUS_VALID);
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);
			}
			
		} catch (Exception ex) {
			this.axisResponse.setStatus("Error");
			this.axisResponse.setException(ex.getMessage());
			return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.SERVICE_UNAVAILABLE);
			
		}
		
	}
	
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<AxisResponse> ValidateSingleUserInfo(@RequestBody UserSingleInfoValidator info) {
		this.axisResponse = new AxisResponse();
		System.out.println("Validator type: " + info.getValidatorType());;
		System.out.println("Received username: " + info.getValue());
		boolean isInfoExist = false;
		
		//validate username
		if(info.getValidatorType() == 0) {
			try {
				isInfoExist = userService.validateUserName(info.getValue());
				this.axisResponse.setStatus(AxisResponseCodes.STATUS_VALID);
				this.axisResponse.setData(Collections.singletonMap("isInfoExist", isInfoExist));
				
			} catch (Exception ex) {
				this.axisResponse.setStatus(AxisResponseCodes.STATUS_INVALID);
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.SERVICE_UNAVAILABLE);
			} 
		} else if(info.getValidatorType() == 1) {
			try {
				isInfoExist = userService.validateEmail(info.getValue());
				this.axisResponse.setStatus(AxisResponseCodes.STATUS_VALID);
				this.axisResponse.setData(Collections.singletonMap("isInfoExist", isInfoExist));
				
			} catch(Exception ex) {
				this.axisResponse.setStatus(AxisResponseCodes.STATUS_INVALID);
				this.axisResponse.setException(ex.getMessage());
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.SERVICE_UNAVAILABLE);
			}
			
		}
	
		return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);	
	}

	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<AxisResponse> create(@RequestBody User user) {	
		
		System.out.println("User: " + user.toString());
		
		this.axisResponse = new AxisResponse();
		
		
		try {
			boolean isDataValid;
			//validate user name
			isDataValid = this.userService.validateUserName(user.getUsername());
			if(isDataValid) {
				this.axisException = new AxisException(AxisResponseCodes.USER_ALREADY_EXISTS);
				this.axisResponse.setStatus(AxisResponseCodes.STATUS_INVALID);
				this.axisResponse.setException(this.axisException.getMessage());
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);
			}
			
			//validate email
			isDataValid = this.userService.validateEmail(user.getEmail());
			if(isDataValid) {
				this.axisException = new AxisException(AxisResponseCodes.EMAIL_ALREADY_EXISTS);
				this.axisResponse.setStatus(AxisResponseCodes.STATUS_INVALID);
				this.axisResponse.setException(this.axisException.getMessage());
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);
			}
			
			//If validation is success then create account
			User userDTO = this.userService.createUser(user);
			
			if(userDTO != null) {
			 Employee employeeDTO =	this.employeeService.createEmployee(userDTO);
			}
			
			
		} catch (NullPointerException ex) {
			this.axisResponse.setStatus(AxisResponseCodes.STATUS_INVALID);
			this.axisResponse.setException(ex.getMessage());
		}
		
		catch(Exception ex) {
			this.axisResponse.setStatus(AxisResponseCodes.STATUS_INVALID);
			this.axisResponse.setException(ex.getMessage());
			
		}
		return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);	
	}
	
	@RequestMapping(value = "timesheet", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<GenericControlList>> getUserProjects() {
		
		ArrayList<GenericControlList> projectList = userService.getUserProjects();
		
		return new ResponseEntity<ArrayList<GenericControlList>>(projectList, HttpStatus.OK);
		
	}
	

}
