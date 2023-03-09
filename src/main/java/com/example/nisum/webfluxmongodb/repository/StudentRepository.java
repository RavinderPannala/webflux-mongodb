package com.example.nisum.webfluxmongodb.repository;

import com.example.nisum.webfluxmongodb.model.Student;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student,Long> {
    Flux<Student> findByFirstName(String name);
}
