package com.sans.axis.domain.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.sans.axis.domain.GenericControlList;
import com.sans.axis.domain.User;


public interface IUserCustomRepository {
	
	public User getUser(String userName, String passWord);
		
	public boolean validateUserName(String username);
	
	public boolean validateEmail(String email);
		
	public User createUser(User user);
	
	ArrayList<GenericControlList> getUserProjectList();

}