package com.example.nisum.webfluxmongodb.zipping.service;

import com.example.nisum.webfluxmongodb.zipping.DTO.DepartmentEmployeeDto;
import com.example.nisum.webfluxmongodb.zipping.Exception.ResourceNotFoundException;
import com.example.nisum.webfluxmongodb.zipping.model.Department;
import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import com.example.nisum.webfluxmongodb.zipping.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeService employeeService;

    public Mono<Department> save(Department department) {
        return departmentRepository.save(department);
    }

    public Flux<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Flux<Department> findByOrganizationId(int orgId) {
        return departmentRepository.findByOrganizationId(orgId);
    }

    public Mono<DepartmentEmployeeDto> findById(int id) {
        List<Employee> employees = new ArrayList<>();
        Mono<DepartmentEmployeeDto> map = departmentRepository
                .findById(id).zipWhen(dept -> {
                    Flux<Employee> employeeFlux = employeeService.findByDepartmentId(dept.getId()).switchIfEmpty(Flux.just(new Employee()));
                    DepartmentEmployeeDto departmentEmployeeDto = new DepartmentEmployeeDto();
                    departmentEmployeeDto.setName(dept.getName());
                    employeeFlux.collectList().subscribe(employees::addAll);
                    departmentEmployeeDto.setEmployeeList(employees);
                    return Mono.just(departmentEmployeeDto);
                }).switchIfEmpty(Mono.error(new ResourceNotFoundException("Department id not found"))).map(tuple -> {
                    DepartmentEmployeeDto t2 = tuple.getT2();
                    return t2;
                });
        return map;
    }
}
