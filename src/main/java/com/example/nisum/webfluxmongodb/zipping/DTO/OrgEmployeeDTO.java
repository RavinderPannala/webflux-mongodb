package com.example.nisum.webfluxmongodb.zipping.DTO;

import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrgEmployeeDTO {

    private String name;
    private List<Employee> employeeList;
}
