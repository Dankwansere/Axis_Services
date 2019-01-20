package com.sans.axis.service;

import com.sans.axis.domain.User;
import com.sans.axis.domain.HR.Employee;

public interface IEmployeeService {
	
	public Employee createEmployee(User user);
}
