package com.example.nisum.webfluxmongodb.zipping.repository;

import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, Integer> {
    Flux<Employee> findByOrganizationId(int orgId);

    Flux<Employee> findByDepartmentId(int departmentId);
}
