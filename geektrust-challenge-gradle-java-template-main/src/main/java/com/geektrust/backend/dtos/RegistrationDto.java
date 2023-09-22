package com.geektrust.backend.dtos;

import com.geektrust.backend.entities.Course;

public class RegistrationDto {

    private final Course course;
    private final String employeeEmail;

    private final String employeeName;

    public RegistrationDto(String employeeEmail, Course course) {
        this.employeeEmail = employeeEmail;
        this.course = course;
        this.employeeName = extractNameFromEmail(employeeEmail);
    }

    private String extractNameFromEmail(String email) {
        return email.split("@")[0];
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Course getCourse() {
        return course;
    }

}
