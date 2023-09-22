package com.geektrust.backend.services;

import com.geektrust.backend.constants.Constants;
import com.geektrust.backend.dtos.RegistrationDto;
import com.geektrust.backend.entities.Course;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.CourseFullErrorException;
import com.geektrust.backend.exceptions.CourseNotFoundException;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.exceptions.RegistrationNotFoundException;
import com.geektrust.backend.repositories.ICourseRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class EmployeeServices implements IEmployeeServices {

    private final ICourseRepository courseRepository;
    private final IRegistrationRepository registrationRepository;

    public EmployeeServices(ICourseRepository courseRepository, IRegistrationRepository registrationRepository) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Registration registerToACourse(String emailAddress, String courseId) throws InputDataErrorException, CourseNotFoundException, CourseFullErrorException {
        validateEmail(emailAddress);
        Optional<Course> course = courseRepository.getCourse(courseId);
        if (course.isEmpty()) {
            throw new CourseNotFoundException(Constants.MESSAGE_OF_COURSE_NOT_FOUND_EXCEPTION);
        }
        checkCourseAvailability(course.get());
        return registrationRepository.createRegistration(new RegistrationDto(emailAddress, course.get()));
    }

    @Override
    public Registration cancelEnrollment(String courseRegistrationId) throws RegistrationNotFoundException {
        Registration registration = validateRegistration(courseRegistrationId);
        Registration.Status updatedStatus = Registration.Status.CONFIRMED.equals(registration.getEnrollmentStatus()) ? Registration.Status.CANCEL_REJECTED : Registration.Status.CANCEL_ACCEPTED;
        return registrationRepository.updateRegistrationStatus(List.of(registration), updatedStatus).get(0);
    }

    private Registration validateRegistration(String registrationId) throws RegistrationNotFoundException {
        Registration registration = registrationRepository.getRegistrationDetails(registrationId);
        if (registration == null) {
            throw new RegistrationNotFoundException(Constants.MESSAGE_OF_REGISTRATION_NOT_FOUND_EXCEPTION);
        }
        return registration;
    }

    private void validateEmail(String emailAddress) throws InputDataErrorException {
        if (!isEmailValid(emailAddress)) {
            throw new InputDataErrorException(Constants.MESSAGE_OF_INPUT_DATA_ERROR_EXCEPTION);
        }
    }

    private void checkCourseAvailability(Course course) throws CourseFullErrorException {
        int numberOfActiveRegistrations = registrationRepository.getAllActiveRegistrationDetailsOfCourse(course.getCourseId()).size();
        if (numberOfActiveRegistrations == course.getMaximumEnrollments()) {
            throw new CourseFullErrorException(Constants.MESSAGE_OF_COURSE_FULL_ERROR_EXCEPTION);
        }
    }

    private boolean isEmailValid(String emailAddress) {
        Pattern pattern = Pattern.compile(Constants.EMAIL_REGEX);
        return pattern.matcher(emailAddress).matches();
    }

}
