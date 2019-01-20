package com.sans.axis.domain.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sans.axis.domain.User;
import com.sans.axis.domain.HR.Employee;
import com.sans.axis.domain.repository.IEmployeeCustomRepository;
import com.sans.axis.domain.repository.IEmployeeRepository;
import com.sans.axis.domain.repository.IUserCustomRepository;

@Repository
public class EmployeeRepositoryImpl implements IEmployeeCustomRepository {
	

	@Autowired
	private IEmployeeRepository employeeRepository;
	
	@Autowired
	private IUserCustomRepository userRepository;
	
	public Employee createEmployee (User userDTO) {
		
		User user = this.userRepository.getUser(userDTO.getUsername(), userDTO.getPassword());
		Employee employeeDTO = userDTO.getEmployee();
		employeeDTO.setEmployeeID(user.getId());
		userDTO.setEmployee(employeeDTO);
		
		
		return this.employeeRepository.save(userDTO.getEmployee());
		
	}


}
