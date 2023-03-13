package com.example.nisum.webfluxmongodb.zipping.service;

import com.example.nisum.webfluxmongodb.zipping.DTO.OrgEmployeeDTO;
import com.example.nisum.webfluxmongodb.zipping.Exception.ResourceNotFoundException;
import com.example.nisum.webfluxmongodb.zipping.model.Department;
import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import com.example.nisum.webfluxmongodb.zipping.model.Organization;
import com.example.nisum.webfluxmongodb.zipping.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

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

    public Mono<OrgEmployeeDTO> findByOrgName(String name) {
        Mono<Tuple2<Organization, List<Employee>>> tuple2Mono = organizationRepository.findByName(name)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Organization details not found with Name")))
                .zipWhen(org -> employeeService.findByOrganizationId(org.getId()).collectList());
        Mono<OrgEmployeeDTO> orgEmployeeDTOMono = tuple2Mono.switchIfEmpty(Mono.error(new ResourceNotFoundException("Employee details not found"))).map(tuple -> {
            Organization org = tuple.getT1();
            List<Employee> employeeList = tuple.getT2();
            OrgEmployeeDTO orgEmployeeDTO = new OrgEmployeeDTO();
            orgEmployeeDTO.setName(org.getName());
            orgEmployeeDTO.setEmployeeList(employeeList);
            return orgEmployeeDTO;
        });
        return orgEmployeeDTOMono;
    }

    public Mono<ResponseEntity<Void>> deleteById(int id) {
        return organizationRepository.findById(id).flatMap(org -> {
            Flux<Department> departmentFlux = departmentService.findByOrganizationId(org.getId());
            departmentService.delete(departmentFlux);
            Flux<Employee> employeeFlux = employeeService.findByOrganizationId(org.getId());
            Mono<Void> delete = employeeService.delete(employeeFlux);
            return organizationRepository.delete(org).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }
}
