package com.employee.entity;

import org.springframework.data.repository.CrudRepository;

public interface IEmployee extends CrudRepository<Employee, Integer> {
}
