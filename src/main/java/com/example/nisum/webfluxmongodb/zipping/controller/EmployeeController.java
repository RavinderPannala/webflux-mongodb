package com.example.nisum.webfluxmongodb.zipping.controller;

import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import com.example.nisum.webfluxmongodb.zipping.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/save")
    private Mono<Employee> save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @RequestMapping("/get")
    private Flux<Employee> getAll() {
        return employeeService.getAll();
    }


    @RequestMapping("/get/org/{orgId}")
    private Flux<Employee> findByOrganizationId(@PathVariable int orgId) {
        return employeeService.findByOrganizationId(orgId);
    }

    @RequestMapping("/get/dept/{departmentId}")
    private Flux<Employee> findByDepartmentId(@PathVariable int departmentId) {
        return employeeService.findByDepartmentId(departmentId);
    }
}
