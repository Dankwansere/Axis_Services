package com.sans.axis.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.sans.axis.domain.Employee;
import com.sans.axis.domain.User;

public interface IEmployeeRepository extends CrudRepository<Employee, Long> {


}
