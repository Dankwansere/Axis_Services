package com.sans.axis.domain.repository;

import com.sans.axis.domain.Employee;
import com.sans.axis.domain.User;

public interface IEmployeeCustomRepository {
	
	public Employee createEmployee (User userDTO);

}
