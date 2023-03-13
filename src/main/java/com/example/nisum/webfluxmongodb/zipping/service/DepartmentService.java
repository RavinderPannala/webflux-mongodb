package com.example.nisum.webfluxmongodb.zipping.service;

import com.example.nisum.webfluxmongodb.zipping.DTO.DepartmentEmployeeDto;
import com.example.nisum.webfluxmongodb.zipping.model.Department;
import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import com.example.nisum.webfluxmongodb.zipping.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

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
        Mono<DepartmentEmployeeDto> map = departmentRepository.findById(id).zipWhen(dept -> employeeService.findByDepartmentId(dept.getId()).collectList()).map(tuple -> {
            DepartmentEmployeeDto departmentEmployeeDto = new DepartmentEmployeeDto();
            Department dept = tuple.getT1();
            List<Employee> employeeList = tuple.getT2();
            departmentEmployeeDto.setName(dept.getName());
            departmentEmployeeDto.setEmployeeList(employeeList);
            return departmentEmployeeDto;
        });
        return map;
    }

    public Flux<DepartmentEmployeeDto> findAllDepartmentEmployees() {
        Flux<DepartmentEmployeeDto> map = departmentRepository.findAll().flatMap(this::apply)
                .map(tuple -> {
                    DepartmentEmployeeDto departmentEmployeeDto = new DepartmentEmployeeDto();
                    departmentEmployeeDto.setName(tuple.getT1().getName());
                    departmentEmployeeDto.setEmployeeList(tuple.getT2());
                    return departmentEmployeeDto;
                });
        return map;
    }

    private Mono<Tuple2<Department, List<Employee>>> apply(Department department) {
        return Mono.just(department)
                .zipWith(employeeService.getAll().filter(it -> it.getDepartmentId() == department.getId()).collectList())
                .map(tuple -> {
                    tuple.getT1().setEmployees(tuple.getT2());
                    return tuple;
                });
    }

    public Mono<Void> deleteDepartment(Department dept) {
        return departmentRepository.delete(dept);
    }

    public Mono<Void> delete(Flux<Department> departmentFlux) {
        return departmentRepository.deleteAll(departmentFlux);
    }
}
