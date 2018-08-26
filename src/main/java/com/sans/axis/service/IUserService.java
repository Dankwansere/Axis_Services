package com.sans.axis.service;

import java.util.ArrayList;

import com.sans.axis.commons.AxisException;
import com.sans.axis.domain.GenericControlList;
import com.sans.axis.domain.User;

public interface IUserService {
	
	
	public User getUser(String userName, String password);
	
	public ArrayList<GenericControlList> getUserProjects();
	
	public User createUser(User user) throws AxisException;
	
	public boolean validateUserName(String username);
	
	public boolean validateEmail(String email);

}
