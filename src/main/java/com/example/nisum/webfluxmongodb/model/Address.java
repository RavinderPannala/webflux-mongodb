package com.example.nisum.webfluxmongodb.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Address {

    @Id
    private Long id;
    private String address;
    private String city;
    private String pincode;
    private String addressType;
    private Long studentId;

}
