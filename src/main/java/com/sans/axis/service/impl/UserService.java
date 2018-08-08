package com.sans.axis.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sans.axis.domain.GenericControlList;
import com.sans.axis.domain.User;
import com.sans.axis.domain.repository.IUserCustomRepository;
import com.sans.axis.service.IUserService;

@Service
public class UserService implements IUserService  {

	@Autowired
	private IUserCustomRepository customUserRepository;
	
	
	@Override
	public User getUser(String userName, String passWord) {
		try {
			User user = customUserRepository.getUser(userName, passWord);	
			if(user != null) {
				user.setPassWord("");
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
	public boolean createUser(User user) {
		if(user.getUserName() == null || user.getFirstName() == null|| user.getEmailAdd() == null) {
			return false;
		}
		else {
			return customUserRepository.createUser(user);
		}
		
	}

}
