package com.example.nisum.webfluxmongodb.repository;

import com.example.nisum.webfluxmongodb.model.Subject;
import com.example.nisum.webfluxmongodb.model.Teachers;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TeacherRepository extends ReactiveMongoRepository<Teachers,Long> {
    Flux<Teachers> findByStudentId(Long studentId);
}
