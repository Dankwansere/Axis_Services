package com.sans.axis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sans.axis.domain.User;
import com.sans.axis.domain.HR.Employee;
import com.sans.axis.domain.repository.IEmployeeCustomRepository;
import com.sans.axis.domain.repository.IEmployeeRepository;
import com.sans.axis.service.IEmployeeService;

@Service
public class EmployeeService implements IEmployeeService {

	@Autowired
	private IEmployeeCustomRepository employeeRepository;
	
	@Override
	public Employee createEmployee(User user) {
		
		return this.employeeRepository.createEmployee(user);
	}

}
