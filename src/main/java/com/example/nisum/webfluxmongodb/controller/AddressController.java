package com.example.nisum.webfluxmongodb.controller;


import com.example.nisum.webfluxmongodb.Service.AddressService;
import com.example.nisum.webfluxmongodb.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/save")
    public Mono<Address> save(@RequestBody Address address){
        return addressService.save(address);
    }

    @GetMapping("/{studentId}")
    public Flux<Address> findByStudentId(@PathVariable Long studentId){
        return addressService.findByStudentId(studentId);
    }

    @GetMapping("/city/{city}")
    public Flux<Address> findByCity(@PathVariable String city){
        return addressService.findByCity(city);
    }

}
