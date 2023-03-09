package com.example.nisum.webfluxmongodb.controller;

import com.example.nisum.webfluxmongodb.Service.SubjectService;
import com.example.nisum.webfluxmongodb.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/save")
    public Mono<Subject> save(@RequestBody Subject subject){
        return subjectService.save(subject);
    }

    @GetMapping("/{studentId}")
    public Flux<Subject> findByStudentId(@PathVariable Long studentId){
        return subjectService.findByStudentId(studentId);
    }
}
