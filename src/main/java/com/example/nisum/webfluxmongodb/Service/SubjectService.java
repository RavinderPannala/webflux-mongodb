package com.example.nisum.webfluxmongodb.Service;

import com.example.nisum.webfluxmongodb.model.Subject;
import com.example.nisum.webfluxmongodb.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    public Mono<Subject> save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Flux<Subject> findByStudentId(Long studentId) {
        return subjectRepository.findByStudentId(studentId);
    }
}
