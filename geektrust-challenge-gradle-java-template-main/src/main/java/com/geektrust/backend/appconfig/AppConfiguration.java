package com.geektrust.backend.appconfig;

import com.geektrust.backend.commands.*;
import com.geektrust.backend.constants.Constants;
import com.geektrust.backend.repositories.CourseRepository;
import com.geektrust.backend.repositories.ICourseRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;
import com.geektrust.backend.repositories.RegistrationRepository;
import com.geektrust.backend.services.EmployeeServices;
import com.geektrust.backend.services.IEmployeeServices;
import com.geektrust.backend.services.IInstructorServices;
import com.geektrust.backend.services.InstructorServices;

public class AppConfiguration {

    private final ICourseRepository courseRepository = new CourseRepository();
    private final IRegistrationRepository registrationRepository = new RegistrationRepository();
    private final IEmployeeServices employeeServices = new EmployeeServices(courseRepository, registrationRepository);
    private final RegisterToACourseCommand registerToACourseCommand = new RegisterToACourseCommand(employeeServices);
    private final CancelEnrollmentCommand cancelEnrollmentCommand = new CancelEnrollmentCommand(employeeServices);
    private final IInstructorServices instructorServices = new InstructorServices(courseRepository, registrationRepository);
    private final AddCourseOfferingCommand addCourseOfferingCommand = new AddCourseOfferingCommand(instructorServices);
    private final AllotCourseCommand allotCourseCommand = new AllotCourseCommand(instructorServices);
    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker() {
        commandInvoker.register(Constants.COMMAND_ADD_COURSE_OFFERING, addCourseOfferingCommand);
        commandInvoker.register(Constants.COMMAND_ALLOT_COURSE, allotCourseCommand);
        commandInvoker.register(Constants.COMMAND_REGISTER_TO_COURSE, registerToACourseCommand);
        commandInvoker.register(Constants.COMMAND_CANCEL_ENROLLMENT, cancelEnrollmentCommand);
        return commandInvoker;
    }

}
