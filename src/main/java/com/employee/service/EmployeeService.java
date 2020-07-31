package com.employee.service;

import com.employee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import com.employee.repository.EmployeeRepository;
import java.util.Comparator;
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
        List<Employee> sortedList = employeeRepository.findAll();
        sortedList.sort(Comparator.comparing(Employee::getFirstName));
        return sortedList;
    }

    public Boolean checkIfEmployeeExists(Employee employee) {
       ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("firstName", new ExampleMatcher.GenericPropertyMatcher().startsWith()).
               withMatcher("dateOfBirth", new ExampleMatcher.GenericPropertyMatcher().exact());

       Example<Employee> employeeExample = Example.of(employee, exampleMatcher);
        return employeeRepository.exists(employeeExample);
    }
}
