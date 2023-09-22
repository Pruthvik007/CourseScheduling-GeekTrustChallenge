package com.geektrust.backend.constants;

public class Constants {

    public static final String COMMAND_ADD_COURSE_OFFERING = "ADD-COURSE-OFFERING";
    public static final String COMMAND_ALLOT_COURSE = "ALLOT";
    public static final String COMMAND_REGISTER_TO_COURSE = "REGISTER";
    public static final String COMMAND_CANCEL_ENROLLMENT = "CANCEL";
    public static final String COURSE_DATE_FORMAT = "ddMMyyyy";
    public static final String MESSAGE_OF_INPUT_DATA_ERROR_EXCEPTION = "INPUT_DATA_ERROR";
    public static final String MESSAGE_OF_COURSE_FULL_ERROR_EXCEPTION = "COURSE_FULL_ERROR";
    public static final String MESSAGE_OF_COURSE_NOT_FOUND_EXCEPTION = "Course Not Found";
    public static final String MESSAGE_OF_REGISTRATION_NOT_FOUND_EXCEPTION = "Registration Not Found";
    public static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private Constants() {
    }

}
