package com.geektrust.backend.commands;

import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.helpers.CommandHelper;
import com.geektrust.backend.helpers.ExceptionHandler;
import com.geektrust.backend.services.IEmployeeServices;

import java.util.List;

public class RegisterToACourseCommand implements ICommand {

    private static final int INDEX_OF_EMAIL_ADDRESS = 0;
    private static final int INDEX_OF_COURSE_ID = 1;
    private static final int REQUIRED_NUMBER_OF_ARGUMENTS = 2;
    private final IEmployeeServices employeeServices;

    public RegisterToACourseCommand(IEmployeeServices employeeServices) {
        this.employeeServices = employeeServices;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            CommandHelper.validateArguments(tokens, REQUIRED_NUMBER_OF_ARGUMENTS);
            Registration registration = employeeServices.registerToACourse(tokens.get(INDEX_OF_EMAIL_ADDRESS), tokens.get(INDEX_OF_COURSE_ID));
            System.out.print(registration.getRegistrationId() + " " + registration.getEnrollmentStatus() + "\n");
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

}
