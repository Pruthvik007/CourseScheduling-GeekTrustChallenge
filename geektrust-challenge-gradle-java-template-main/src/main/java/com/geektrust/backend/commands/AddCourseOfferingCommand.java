package com.geektrust.backend.commands;

import com.geektrust.backend.constants.Constants;
import com.geektrust.backend.dtos.CourseDto;
import com.geektrust.backend.entities.Course;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.helpers.CommandHelper;
import com.geektrust.backend.helpers.ExceptionHandler;
import com.geektrust.backend.services.IInstructorServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AddCourseOfferingCommand implements ICommand {

    private static final int INDEX_OF_COURSE_NAME = 0;
    private static final int INDEX_OF_INSTRUCTOR_NAME = 1;
    private static final int INDEX_OF_START_DATE = 2;
    private static final int INDEX_OF_MINIMUM_EMPLOYEES = 3;
    private static final int INDEX_OF_MAXIMUM_EMPLOYEES = 4;
    private static final int REQUIRED_NUMBER_OF_ARGUMENTS = 5;
    private final IInstructorServices instructorServices;

    public AddCourseOfferingCommand(IInstructorServices instructorServices) {
        this.instructorServices = instructorServices;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            CommandHelper.validateArguments(tokens, REQUIRED_NUMBER_OF_ARGUMENTS);
            System.out.print(executeAddCourseOffering(tokens).getCourseId() + "\n");
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    private Course executeAddCourseOffering(List<String> tokens) {
        try {
            CourseDto courseDto = new CourseDto(tokens.get(INDEX_OF_COURSE_NAME), tokens.get(INDEX_OF_INSTRUCTOR_NAME), new SimpleDateFormat(Constants.COURSE_DATE_FORMAT).parse(tokens.get(INDEX_OF_START_DATE)), Integer.parseInt(tokens.get(INDEX_OF_MINIMUM_EMPLOYEES)), Integer.parseInt(tokens.get(INDEX_OF_MAXIMUM_EMPLOYEES)));
            return instructorServices.addCourseOffering(courseDto);
        } catch (ParseException e) {
            throw new InputDataErrorException(Constants.MESSAGE_OF_INPUT_DATA_ERROR_EXCEPTION);
        }
    }

}
