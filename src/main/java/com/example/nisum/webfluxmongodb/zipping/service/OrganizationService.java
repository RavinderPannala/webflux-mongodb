package com.example.nisum.webfluxmongodb.zipping.service;

import com.example.nisum.webfluxmongodb.zipping.Exception.ResourceNotFoundException;
import com.example.nisum.webfluxmongodb.zipping.model.Department;
import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import com.example.nisum.webfluxmongodb.zipping.model.Organization;
import com.example.nisum.webfluxmongodb.zipping.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    public Mono<Organization> save(Organization organization) {
        return organizationRepository.save(organization);
    }

    public Flux<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public Mono<Organization> findById(int id) {
        Mono<Organization> organizationMono = organizationRepository.findById(id).zipWhen(org -> {
            Flux<Department> departmentFlux = departmentService.findByOrganizationId(org.getId()).switchIfEmpty(Flux.just(new Department()));
            Flux<Employee> employeeFlux = employeeService.findByOrganizationId(org.getId()).switchIfEmpty(Flux.just(new Employee()));
            return departmentFlux.collectList().zipWith(employeeFlux.collectList());
        }).map(tuple -> {
            Organization organization = tuple.getT1();
            Tuple2<List<Department>, List<Employee>> departmentEmployeeTuple = tuple.getT2();
            organization.setDepartments(departmentEmployeeTuple.getT1());
            organization.setEmployees(departmentEmployeeTuple.getT2());
            return organization;
        }).switchIfEmpty(Mono.error(new ResourceNotFoundException("Resource not found")));

        return organizationMono;
    }
}
