package com.example.nisum.webfluxmongodb.repository;

import com.example.nisum.webfluxmongodb.model.Address;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AddressRepository extends ReactiveMongoRepository<Address,Long> {

    Flux<Address> findByStudentId(Long studentId);

    Flux<Address> findByCity(String location);
}
