package com.employee.service;

import com.employee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.employee.repository.EmployeeRepository;

import java.util.List;

@Service
@Primary
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void save(Employee newEmployee) {
        employeeRepository.save(newEmployee);
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
