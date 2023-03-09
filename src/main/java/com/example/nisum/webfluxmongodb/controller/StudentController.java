package com.example.nisum.webfluxmongodb.controller;

import com.example.nisum.webfluxmongodb.Service.StudentService;
import com.example.nisum.webfluxmongodb.dto.StudentBasicDto;
import com.example.nisum.webfluxmongodb.dto.StudentDto;
import com.example.nisum.webfluxmongodb.model.Address;
import com.example.nisum.webfluxmongodb.model.Student;
import com.example.nisum.webfluxmongodb.model.Subject;
import com.example.nisum.webfluxmongodb.model.Teachers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/save")
    public Mono<Student> saveStudent(@RequestBody StudentDto studentDto){
        return studentService.save(studentDto);
    }

    @GetMapping(value="/getAll")
    public Flux<Student> getAll(){
        return studentService.getAll();
    }

    @GetMapping(value="/{studentId}")
    public Mono<Student> findByStudentId(@PathVariable Long studentId){
        Mono<Student> student = studentService.findById(studentId);
        student.subscribe(student1 -> System.out.println(student1));
        return student;
    }

    @GetMapping(value = "/searchNames/{names}")
    public Flux<Student> searchNames(@PathVariable String names){
        Flux<Student> studentFlux = studentService.searchNames(names);
        return studentFlux;
    }

    @GetMapping("/searchNamesWithZip/{name}")
    public Flux<Tuple2<Tuple2<Tuple2<Student, Address>, Subject>, Teachers>> searchNamesWithZip(@PathVariable String name){
        return studentService.searchNamesWithZip(name);
    }

    @GetMapping("/getStudentNames")
    public Flux<Tuple2<String, String>> studentNames(){
        //Flux<Tuple2<String, String>> tuple2Flux = studentService.studentNames();
        return studentService.studentNames();
    }

    @GetMapping("/studentsBasicInfo")
    public Flux<StudentBasicDto> studentBasicInformation(){
        Flux<StudentBasicDto> studentBasicDtoFlux = studentService.studentBasicInformation();
        return studentBasicDtoFlux;
    }

    @GetMapping("/getStudents/{city}")
    public Flux<Student> findStudentsWhoBelogstoSpecifiedLocation(@PathVariable String city){
        return studentService.findStudentsWhoBelogstoSpecifiedLocation(city);
    }
}
