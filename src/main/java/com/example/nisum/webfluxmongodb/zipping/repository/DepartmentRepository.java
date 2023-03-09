package com.example.nisum.webfluxmongodb.zipping.repository;

import com.example.nisum.webfluxmongodb.zipping.model.Department;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DepartmentRepository extends ReactiveMongoRepository<Department,Integer> {
    Flux<Department> findByOrganizationId(int orgId);
}
