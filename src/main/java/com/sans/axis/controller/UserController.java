package com.sans.axis.controller;

import java.util.ArrayList;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.sans.axis.service.impl.JwtService;
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
		HttpHeaders responseHeaders = new HttpHeaders();
		
		
		try {
			this.user = userService.getUser(user.getUsername(), user.getPassword());
			if(this.user == null) {
				this.axisResponse.setStatus(AxisResponseCodes.STATUS_INVALID);
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK) ;
			} else {
				responseHeaders.add("Authorization", JwtService.genJwtToken(user));
				responseHeaders.add("access-control-expose-headers", "Authorization");
				this.axisResponse = AxisResponse.setAxisResponse(AxisResponseCodes.STATUS_VALID, this.user);
				return new ResponseEntity<AxisResponse>(this.axisResponse, responseHeaders, HttpStatus.OK) ;
			}
			
		} catch (Exception ex) {
			this.axisResponse = AxisResponse.setErrorAxisResponse("Error", ex.getMessage());
			return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.SERVICE_UNAVAILABLE);
			
		}
		
	}
	
	
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<AxisResponse> ValidateSingleUserInfo(@RequestBody UserSingleInfoValidator info) {
		this.axisResponse = new AxisResponse();
		boolean isInfoExist = false;
		
		//validate user name
		if(info.getValidatorType() == 0) {
			try {
				isInfoExist = userService.validateUserName(info.getValue());
				this.axisResponse = AxisResponse.setAxisResponse(AxisResponseCodes.STATUS_VALID, Collections.singletonMap("isInfoExist", isInfoExist));
				
			} catch (Exception ex) {
				this.axisResponse.setStatus(AxisResponseCodes.STATUS_INVALID);
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.SERVICE_UNAVAILABLE);
			} 
		} else if(info.getValidatorType() == 1) {
			try {
				isInfoExist = userService.validateEmail(info.getValue());
				this.axisResponse = AxisResponse.setAxisResponse(AxisResponseCodes.STATUS_VALID, Collections.singletonMap("isInfoExist", isInfoExist));

				
			} catch(Exception ex) {
				this.axisResponse = AxisResponse.setErrorAxisResponse(AxisResponseCodes.STATUS_INVALID, ex.getMessage());
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.SERVICE_UNAVAILABLE);
			}
		}
	
		return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);	
	}

	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<AxisResponse> create(@RequestBody User user) {	
		
		this.axisResponse = new AxisResponse();		
		try {
			boolean isDataValid;
			//validate user name
			isDataValid = this.userService.validateUserName(user.getUsername());
			if(isDataValid) {
				this.axisException = new AxisException(AxisResponseCodes.USER_ALREADY_EXISTS);
				this.axisResponse = AxisResponse.setErrorAxisResponse(AxisResponseCodes.STATUS_INVALID, this.axisException.getMessage());
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);
			}
			
			//validate email
			isDataValid = this.userService.validateEmail(user.getEmail());
			if(isDataValid) {
				this.axisException = new AxisException(AxisResponseCodes.EMAIL_ALREADY_EXISTS);
				this.axisResponse = AxisResponse.setErrorAxisResponse(AxisResponseCodes.STATUS_INVALID, this.axisException.getMessage());
				return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);
			}
			
			//If validation is success then create account
			User userDTO = this.userService.createUser(user);
			Employee employeeDTO;
			if(userDTO != null) {
				employeeDTO  =	this.employeeService.createEmployee(userDTO);
			} else {
				 this.axisResponse = AxisResponse.setErrorAxisResponse(AxisResponseCodes.STATUS_INVALID, this.axisException.getMessage());
				 return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);	
			}
			
			if(employeeDTO != null) {
				this.axisResponse = AxisResponse.setAxisResponse(AxisResponseCodes.STATUS_VALID, Collections.singletonMap("isUserCreated", true));
				
			} else {
				this.axisResponse = AxisResponse.setAxisResponse(AxisResponseCodes.STATUS_INVALID, Collections.singletonMap("isUserCreated", true));
			}
			
		} catch (NullPointerException ex) {
			this.axisResponse = AxisResponse.setErrorAxisResponse(AxisResponseCodes.STATUS_INVALID, ex.getMessage());
		}
		
		catch(Exception ex) {
			this.axisResponse = AxisResponse.setErrorAxisResponse(AxisResponseCodes.STATUS_INVALID, ex.getMessage());
			
		}
		return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK);	
	}
	
	@RequestMapping(value = "timesheet", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<GenericControlList>> getUserProjects() {
		
		ArrayList<GenericControlList> projectList = userService.getUserProjects();
		
		return new ResponseEntity<ArrayList<GenericControlList>>(projectList, HttpStatus.OK);
		
	}
	
}
