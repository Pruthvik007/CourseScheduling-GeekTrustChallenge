package com.geektrust.backend.helpers;

import com.geektrust.backend.exceptions.CourseSchedulingCustomException;

public class ExceptionHandler {

    private ExceptionHandler() {

    }

    public static void handleException(Exception exception) {
        if (exception instanceof CourseSchedulingCustomException) {
            System.out.print(exception.getMessage() + "\n");
            return;
        }
        exception.printStackTrace();
    }

}
