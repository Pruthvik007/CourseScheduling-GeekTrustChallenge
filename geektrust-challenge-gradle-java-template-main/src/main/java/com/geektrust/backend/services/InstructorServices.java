package com.geektrust.backend.services;

import com.geektrust.backend.constants.Constants;
import com.geektrust.backend.dtos.CourseDto;
import com.geektrust.backend.entities.Course;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.CourseNotFoundException;
import com.geektrust.backend.repositories.ICourseRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class InstructorServices implements IInstructorServices {

    private final ICourseRepository courseRepository;
    private final IRegistrationRepository registrationRepository;

    public InstructorServices(ICourseRepository courseRepository, IRegistrationRepository registrationRepository) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Course addCourseOffering(CourseDto courseDto) {
        return courseRepository.createCourseOffering(courseDto);
    }

    @Override
    public List<Registration> allotCourse(String courseId) throws CourseNotFoundException {
        Optional<Course> course = courseRepository.getCourse(courseId);
        if (course.isEmpty()) {
            throw new CourseNotFoundException(Constants.MESSAGE_OF_COURSE_NOT_FOUND_EXCEPTION);
        }
        List<Registration> activeRegistrations = registrationRepository.getAllActiveRegistrationDetailsOfCourse(courseId);
        List<Registration> updatedRegistrations = new ArrayList<>(allotOrCancelRegistrations(activeRegistrations, course.get()));
        updatedRegistrations.sort(Comparator.comparing(Registration::getRegistrationId));
        return updatedRegistrations;
    }

    private List<Registration> allotOrCancelRegistrations(List<Registration> activeRegistrations, Course course) {
        if (activeRegistrations.size() >= course.getMinimumEnrollments()) {
            return registrationRepository.updateRegistrationStatus(activeRegistrations, Registration.Status.CONFIRMED);
        }
        return registrationRepository.updateRegistrationStatus(activeRegistrations, Registration.Status.COURSE_CANCELED);
    }

}
