package com.example.nisum.webfluxmongodb.dto;

import com.example.nisum.webfluxmongodb.model.Address;
import com.example.nisum.webfluxmongodb.model.Subject;
import com.example.nisum.webfluxmongodb.model.Teachers;
import lombok.Value;

public class StudentBasicDto {

    private Long id;
    private String fullName;
    private Address presentAddress;
    private String primarySubject;
    private Teachers firstTeacher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Address getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(Address presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPrimarySubject() {
        return primarySubject;
    }

    public void setPrimarySubject(String primarySubject) {
        this.primarySubject = primarySubject;
    }

    public Teachers getFirstTeacher() {
        return firstTeacher;
    }

    public void setFirstTeacher(Teachers firstTeacher) {
        this.firstTeacher = firstTeacher;
    }

    public StudentBasicDto(Long id, String fullName, Address presentAddress, String primarySubject, Teachers firstTeacher) {
        this.id = id;
        this.fullName = fullName;
        this.presentAddress = presentAddress;
        this.primarySubject = primarySubject;
        this.firstTeacher = firstTeacher;
    }

    public StudentBasicDto() {
    }

    @Override
    public String toString() {
        return "StudentBasicDto{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", presentAddress=" + presentAddress +
                ", primarySubject=" + primarySubject +
                ", firstTeacher=" + firstTeacher +
                '}';
    }
}
