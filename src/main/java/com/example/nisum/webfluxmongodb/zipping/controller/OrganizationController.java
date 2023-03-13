package com.example.nisum.webfluxmongodb.zipping.controller;

import com.example.nisum.webfluxmongodb.zipping.DTO.OrgEmployeeDTO;
import com.example.nisum.webfluxmongodb.zipping.Exception.ResourceNotFoundException;
import com.example.nisum.webfluxmongodb.zipping.model.Organization;
import com.example.nisum.webfluxmongodb.zipping.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    //Get All employees based on Organization Id
    @GetMapping("/get/{id}")
    private Mono<Organization> findById(@PathVariable int id) {
        Mono<Organization> organizationMono = organizationService.findById(id);
        organizationMono.switchIfEmpty(Mono.error(new ResourceNotFoundException("Resource not found")));
        return organizationMono;
    }

    //Get ALl EMployees by OrganizationName
    @GetMapping("/getByName/{name}")
    private Mono<OrgEmployeeDTO> findByOrgName(@PathVariable String name){
       return organizationService.findByOrgName(name);
    }

    @DeleteMapping("/delete/{id}")
    private Mono<ResponseEntity<Void>> delete(@PathVariable int id){
        return organizationService.deleteById(id);
    }
}
