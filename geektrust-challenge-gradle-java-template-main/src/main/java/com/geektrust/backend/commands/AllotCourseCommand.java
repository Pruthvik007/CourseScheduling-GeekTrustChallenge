package com.geektrust.backend.commands;

import com.geektrust.backend.constants.Constants;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.helpers.CommandHelper;
import com.geektrust.backend.helpers.ExceptionHandler;
import com.geektrust.backend.services.IInstructorServices;

import java.text.SimpleDateFormat;
import java.util.List;

public class AllotCourseCommand implements ICommand {

    private static final int INDEX_OF_COURSE_ID = 0;
    private static final int REQUIRED_NUMBER_OF_ARGUMENTS = 1;
    private final IInstructorServices instructorServices;

    public AllotCourseCommand(IInstructorServices instructorServices) {
        this.instructorServices = instructorServices;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            CommandHelper.validateArguments(tokens, REQUIRED_NUMBER_OF_ARGUMENTS);
            List<Registration> updatedRegistrations = instructorServices.allotCourse(tokens.get(INDEX_OF_COURSE_ID));
            System.out.print(getRegistrationDetails(updatedRegistrations));
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private String getRegistrationDetails(List<Registration> registrations) {
        StringBuilder registrationDetails = new StringBuilder();
        registrations.forEach(registration -> registrationDetails.append(registration.getRegistrationId())
                .append(" ").append(registration.getEmployeeEmail()).append(" ")
                .append(registration.getCourse().getCourseId()).append(" ")
                .append(registration.getCourse().getCourseName()).append(" ")
                .append(registration.getCourse().getInstructor()).append(" ")
                .append(new SimpleDateFormat(Constants.COURSE_DATE_FORMAT).format(registration.getCourse().getStartDate()))
                .append(" ").append(registration.getEnrollmentStatus()).append("\n"));
        return registrationDetails.toString();
    }

}
