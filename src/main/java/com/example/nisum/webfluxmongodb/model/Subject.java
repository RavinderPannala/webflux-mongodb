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
public class Subject {


    @Id
    private Long id;
    private String subjectName;
    private Long studentId;

}
