package com.geektrust.backend.exceptions;

public class CourseNotFoundException extends CourseSchedulingCustomException {

    public CourseNotFoundException(String message) {
        super(message);
    }

}
