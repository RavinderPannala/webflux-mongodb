package com.example.nisum.webfluxmongodb.zipping.controller;

import com.example.nisum.webfluxmongodb.zipping.DTO.DepartmentEmployeeDto;
import com.example.nisum.webfluxmongodb.zipping.model.Department;
import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import com.example.nisum.webfluxmongodb.zipping.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/save")
    private Mono<Department> save(@RequestBody Department department) {
        return departmentService.save(department);
    }

    @RequestMapping("/get")
    private Flux<Department> getAll() {
        return departmentService.getAll();
    }

    @RequestMapping("/get/org/{orgId}")
    private Flux<Department> getByOrgId(@PathVariable int orgId){
    return departmentService.findByOrganizationId(orgId);
    }

    //Get Department by Id
    @RequestMapping("/get/{deptId}")
    private Mono<DepartmentEmployeeDto> findByDepartmentId(@PathVariable int deptId){
      return  departmentService.findById(deptId);
    }

    @RequestMapping("/getAll")
    private Flux<DepartmentEmployeeDto> findAllDepartmentAndEmployees(){
        return departmentService.findAllDepartmentEmployees();
    }


}
