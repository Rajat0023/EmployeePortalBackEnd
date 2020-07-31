package com.employee.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.employee.entity.Employee;
import com.employee.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.employee.service.EmployeeService;

@RestController
@CrossOrigin(value = "*", maxAge = 1L)
@RequestMapping("employee/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerEmployee(@RequestBody final Employee employeeRequest) throws SignUpRestrictedException {
        Gson gson = new Gson();
        if (employeeRequest.getFirstName().equals("") || employeeRequest.getDateOfBirth().equals("") || employeeRequest.getGender().equals("")
                || employeeRequest.getDepartment().equals("")) {
            throw new SignUpRestrictedException("SGR - 001", "Except last name all fields should be filled");
        }
        JsonObject jsonObject = new JsonObject();
        ResponseEntity<String> response = null;
        if (!employeeService.checkIfEmployeeExists(employeeRequest)) {
            Employee newEmployee = new Employee();
            newEmployee.setFirstName(employeeRequest.getFirstName());
            newEmployee.setLastName(employeeRequest.getLastName());
            newEmployee.setGender(employeeRequest.getGender());
            newEmployee.setDateOfBirth(employeeRequest.getDateOfBirth());
            newEmployee.setDepartment(employeeRequest.getDepartment());
            employeeService.save(newEmployee);
            jsonObject.addProperty("message", "Employee Registered Successfully");
            response = ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(jsonObject));
        } else {
            jsonObject.addProperty("message", "Employee Already Exists");
            response = ResponseEntity.status(HttpStatus.CONFLICT).body(gson.toJson(jsonObject));
        }
        return response;
    }

    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEmployees() {
        Gson gson = new Gson();
        JsonArray jsonArray = new Gson().toJsonTree(employeeService.getAllEmployees()).getAsJsonArray();
        JsonObject jsonObject  = new JsonObject();
        jsonObject.add("employees", jsonArray);
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(jsonObject));
    }
}
