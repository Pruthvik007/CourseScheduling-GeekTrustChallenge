package com.geektrust.backend.exceptions;

public class CourseFullErrorException extends CourseSchedulingCustomException {

    public CourseFullErrorException(String message) {
        super(message);
    }

}
