package com.example.nisum.webfluxmongodb.zipping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Department {

    @Id
    private int id;

    private String name;

    private int organizationId;

    private List<Employee> employees;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Department(int id, String name, int organizationId, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.organizationId = organizationId;
        this.employees = employees;
    }

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(int id, String name, int organizationId) {
        this.id = id;
        this.name = name;
        this.organizationId = organizationId;
    }

    public Department() {
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", organizationId=" + organizationId +
                ", employees=" + employees +
                '}';
    }
}
