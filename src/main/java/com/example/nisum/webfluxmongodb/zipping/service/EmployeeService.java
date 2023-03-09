package com.example.nisum.webfluxmongodb.zipping.service;

import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import com.example.nisum.webfluxmongodb.zipping.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Mono<Employee> save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Flux<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Flux<Employee> findByOrganizationId(int orgId) {
        return employeeRepository.findByOrganizationId(orgId);
    }

    public Flux<Employee> findByDepartmentId(int departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }
}
