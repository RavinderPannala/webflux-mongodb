package com.example.nisum.webfluxmongodb.dto;

import com.example.nisum.webfluxmongodb.model.Address;
import com.example.nisum.webfluxmongodb.model.Subject;
import com.example.nisum.webfluxmongodb.model.Teachers;

import java.util.List;


public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String age;
    private List<String> phoneNumbers;
    private List<Address> addresses;
    private List<Subject> subjects;
    private List<Teachers> teachers;

    public StudentDto(Long id, String firstName, String lastName, String age, List<String> phoneNumbers, List<Address> addresses, List<Subject> subjects, List<Teachers> teachers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumbers = phoneNumbers;
        this.addresses = addresses;
        this.subjects = subjects;
        this.teachers = teachers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Teachers> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teachers> teachers) {
        this.teachers = teachers;
    }

    public StudentDto() {
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", addresses=" + addresses +
                ", subjects=" + subjects +
                ", teachers=" + teachers +
                '}';
    }
}
