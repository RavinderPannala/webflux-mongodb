package com.example.nisum.webfluxmongodb.zipping.controller;

import com.example.nisum.webfluxmongodb.zipping.Exception.ResourceNotFoundException;
import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import com.example.nisum.webfluxmongodb.zipping.model.Organization;
import com.example.nisum.webfluxmongodb.zipping.service.EmployeeService;
import com.example.nisum.webfluxmongodb.zipping.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/org")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/save")
    private Mono<Organization> save(@RequestBody Organization organization) {
        return organizationService.save(organization);
    }

    @RequestMapping("/get")
    private Flux<Organization> getAll() {
        return organizationService.getAll();
    }

    @RequestMapping("/get/{id}")
    private Mono<Organization> findById(@PathVariable int id) {
        Mono<Organization> organizationMono = organizationService.findById(id);
        organizationMono.switchIfEmpty(Mono.error(new ResourceNotFoundException("Resource not found")));
        return organizationMono;
    }
}
