package com.example.nisum.webfluxmongodb.Aggregation.service;

import com.example.nisum.webfluxmongodb.Aggregation.model.Population;
import com.example.nisum.webfluxmongodb.Aggregation.model.StatePopulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;


@Service
public class AggregationService {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public Flux<StatePopulation> getAllStatePopulation() {
        GroupOperation groupOperation = Aggregation.group("state").sum("pop").as("StatePop");
        SortOperation sort = Aggregation.sort(Sort.by(Collections.singletonList(Sort.Order.asc("state"))));
        Aggregation aggregation = Aggregation.newAggregation(groupOperation, sort);
        Flux<StatePopulation> aggregate = reactiveMongoTemplate.aggregate(aggregation, StatePopulation.class, StatePopulation.class);
         return aggregate;
    }

    public Mono<Population> getAllStates(){
        Query query = new Query(Criteria.where("city").is("AGAWAM"));

        Mono<Population> all = reactiveMongoTemplate.findOne(query, Population.class);
        return all;
    }


}
