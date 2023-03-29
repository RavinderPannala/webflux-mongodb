package com.example.nisum.webfluxmongodb.Aggregation.controller;


import com.example.nisum.webfluxmongodb.Aggregation.model.Population;
import com.example.nisum.webfluxmongodb.Aggregation.model.StatePopulation;
import com.example.nisum.webfluxmongodb.Aggregation.service.AggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/aggregation")
public class AggregationController {

    @Autowired
    private AggregationService aggregationService;

    @RequestMapping("/pop")
    private Flux<StatePopulation> getStatePopulation(){
        Flux<StatePopulation> allStatePopulation = aggregationService.getAllStatePopulation();
        return allStatePopulation;
    }

    @RequestMapping("/getAll")
    private Mono<Population> getAll(){
        Mono<Population> allStates = aggregationService.getAllStates();
        allStates.subscribe(s->System.out.println("Output-->"+s));
        return allStates;
    }
}
