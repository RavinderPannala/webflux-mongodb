package com.example.nisum.webfluxmongodb.zipping.DTO;

import com.example.nisum.webfluxmongodb.zipping.model.Employee;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEmployeeDto {

    private String name;
    private List<Employee> employeeList;
}
