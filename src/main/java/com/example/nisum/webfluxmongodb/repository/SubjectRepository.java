package com.example.nisum.webfluxmongodb.repository;

import com.example.nisum.webfluxmongodb.model.Subject;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface SubjectRepository extends ReactiveMongoRepository<Subject,Long> {
    Flux<Subject> findByStudentId(Long studentId);
}
