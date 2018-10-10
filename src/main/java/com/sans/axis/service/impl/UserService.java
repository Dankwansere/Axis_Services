package com.sans.axis.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sans.axis.commons.AxisException;
import com.sans.axis.commons.AxisResponseCodes;
import com.sans.axis.domain.GenericControlList;
import com.sans.axis.domain.User;
import com.sans.axis.domain.repository.IUserCustomRepository;
import com.sans.axis.service.IUserService;

@Service
public class UserService implements IUserService, UserDetailsService   {

	@Autowired
	private IUserCustomRepository customUserRepository;
	
	
	@Override
	public User getUser(String userName, String passWord) {
		try {
			User user = customUserRepository.getUser(userName, passWord);	
			if(user != null) {
				user.setPassword("");
				return user;
			}
			else {
				System.out.println("Unable to find user: " + userName);
				return null;
			}
		} catch (Exception e) {
			throw e;
			//return null;
		}
	}
	
	@Override
	public ArrayList<GenericControlList> getUserProjects() {
		return customUserRepository.getUserProjectList();
	}
	
	@Override
	public boolean validateUserName(String username) {
		return customUserRepository.validateUserName(username);
	}
	
	@Override
	public boolean validateEmail(String email) {
		return customUserRepository.validateEmail(email);
	}
	
	
	@Override
	public User createUser(User user) throws AxisException {
		if(user.getUsername() == null || user.getFirstname() == null|| user.getEmail() == null) {
			throw new NullPointerException();
		} else if(user.getUsername().equals("") || user.getFirstname().equals("") || user.getLastname().equals("")) {
			throw new AxisException(AxisResponseCodes.CREATE_REQUIRED_NAMES);
		} else if(user.getEmail().equals("")) {
			throw new AxisException(AxisResponseCodes.CREATE_EMAIL_EMPTY);
		}
		else {
			return customUserRepository.createUser(user);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.customUserRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username); 
		}
		
		return new User(user.getUsername(), user.getPassword());
	}

}
