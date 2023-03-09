package com.example.nisum.webfluxmongodb.Service;

import com.example.nisum.webfluxmongodb.model.Address;
import com.example.nisum.webfluxmongodb.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    public Mono<Address> save(Address address) {
        return addressRepository.save(address);
    }

    public Flux<Address> findByStudentId(Long studentId) {
        return addressRepository.findByStudentId(studentId);
    }

    public Flux<Address> findByCity(String city) {
        return addressRepository.findByCity(city);
    }
}
