package com.example.nisum.webfluxmongodb.zipping.repository;

import com.example.nisum.webfluxmongodb.zipping.model.Organization;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

@Repository
public interface OrganizationRepository extends ReactiveMongoRepository<Organization,Integer> {
}
