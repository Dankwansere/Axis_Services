package com.sans.axis.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.sans.axis.domain.User;
import com.sans.axis.domain.HR.Employee;

public interface IEmployeeRepository extends CrudRepository<Employee, Long> {


}
