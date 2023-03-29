package com.example.nisum.webfluxmongodb.Aggregation.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Population {

    @Id
    private String id;
    private String city;
    private String[] location;
    private String state;
    private double population;
}
