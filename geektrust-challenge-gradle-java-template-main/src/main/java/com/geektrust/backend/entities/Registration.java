package com.geektrust.backend.entities;

public class Registration {

    private final String registrationId;
    private final String employeeEmail;
    private final String employeeName;
    private final Course course;
    private final Status enrollmentStatus;

    public Registration(String registrationId, String employeeEmail, String employeeName, Course course, Status status) {
        this.registrationId = registrationId;
        this.employeeEmail = employeeEmail;
        this.employeeName = employeeName;
        this.course = course;
        this.enrollmentStatus = status;
    }

    public String getRegistrationId() {
        return registrationId;
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

    public Status getEnrollmentStatus() {
        return enrollmentStatus;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId='" + registrationId + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", course=" + course +
                ", enrollmentStatus=" + enrollmentStatus +
                '}';
    }

    public enum Status {
        ACCEPTED, CONFIRMED, CANCEL_ACCEPTED, CANCEL_REJECTED, COURSE_CANCELED
    }

}
