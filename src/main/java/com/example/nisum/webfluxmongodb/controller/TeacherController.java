package com.example.nisum.webfluxmongodb.controller;

import com.example.nisum.webfluxmongodb.Service.TeacherService;
import com.example.nisum.webfluxmongodb.model.Subject;
import com.example.nisum.webfluxmongodb.model.Teachers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/save")
    public Mono<Teachers> save(@RequestBody Teachers teachers){
        return teacherService.save(teachers);
    }

    @GetMapping("/{studentId}")
    public Flux<Teachers> findByStudentId(@PathVariable Long studentId){
        return teacherService.findByStudentId(studentId);
    }
}
