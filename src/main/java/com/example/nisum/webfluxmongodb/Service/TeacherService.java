package com.example.nisum.webfluxmongodb.Service;

import com.example.nisum.webfluxmongodb.model.Subject;
import com.example.nisum.webfluxmongodb.model.Teachers;
import com.example.nisum.webfluxmongodb.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Mono<Teachers> save(Teachers teachers) {
       return teacherRepository.save(teachers);
    }

    public Flux<Teachers> findByStudentId(Long studentId) {
        return teacherRepository.findByStudentId(studentId);
    }
}
