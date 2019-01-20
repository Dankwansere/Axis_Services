package com.sans.axis.domain.repository;

import com.sans.axis.domain.User;
import com.sans.axis.domain.HR.Employee;

public interface IEmployeeCustomRepository {
	
	public Employee createEmployee (User userDTO);

}
