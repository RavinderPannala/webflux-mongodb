package com.example.nisum.webfluxmongodb.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Teachers {

    @Id
    private Long id;
    private String teacherName;
    private String teacherSubject;
    private Long studentId;

}
