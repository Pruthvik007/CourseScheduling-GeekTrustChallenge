package com.geektrust.backend.services;

import com.geektrust.backend.dtos.RegistrationDto;
import com.geektrust.backend.entities.Course;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.CourseFullErrorException;
import com.geektrust.backend.exceptions.CourseNotFoundException;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.exceptions.RegistrationNotFoundException;
import com.geektrust.backend.repositories.ICourseRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Employee Services Tests")
@ExtendWith(MockitoExtension.class)
class EmployeeServicesTest {

    @InjectMocks
    private EmployeeServices employeeServices;
    @Mock
    private ICourseRepository courseRepositoryMock;
    @Mock
    private IRegistrationRepository registrationRepositoryMock;

    @DisplayName("Test Register Employee To A Course Creates and Returns Registration Details")
    @Test
    void testRegisterToACourseReturnsRegistrationDetails() {
        String emailAddress = "test@mail.com";
        String courseId = "test_course_id";
        Course course = new Course(courseId, "Java", "Instructor Madison", new Date(), 1, 5);
        Registration registrationCreated = new Registration("registration_id", emailAddress, "test", course, Registration.Status.ACCEPTED);
        when(courseRepositoryMock.getCourse(courseId)).thenReturn(Optional.of(course));
        when(registrationRepositoryMock.getAllActiveRegistrationDetailsOfCourse(courseId)).thenReturn(new ArrayList<>());
        when(registrationRepositoryMock.createRegistration(any(RegistrationDto.class))).thenReturn(registrationCreated);
        Registration.Status expectedRegistrationStatus = Registration.Status.ACCEPTED;
        Registration.Status actualRegistrationStatus = employeeServices.registerToACourse(emailAddress, courseId).getEnrollmentStatus();
        assertEquals(expectedRegistrationStatus, actualRegistrationStatus);
    }

    @DisplayName("Test Register Employee To A Course Throws Exception If Email Is Invalid")
    @Test
    void testRegisterToACourseWhenEmailInvalidThenThrowInputDataErrorException() {
        assertThrows(InputDataErrorException.class, () -> employeeServices.registerToACourse("invalidEmail", "courseId"));
    }

    @DisplayName("Test Register To A Course Throws Exception If Course Not Found")
    @Test
    void testRegisterToACourseWhenCourseNotFoundThenThrowCourseNotFoundException() {
        when(courseRepositoryMock.getCourse("nonExistentCourseId")).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> employeeServices.registerToACourse("valid.email@example.com", "nonExistentCourseId"));
    }

    @DisplayName("Test Prevent Employee From Registering To Course If Course Has Reached Maximum Employees")
    @Test
    void testRegisterToACourseWhenCourseFullThenThrowCourseFullErrorException() {
        Course fullCourse = new Course("fullCourseId", "Full Course", "Instructor", new Date(), 1, 1);
        when(courseRepositoryMock.getCourse(anyString())).thenReturn(Optional.of(fullCourse));
        when(registrationRepositoryMock.getAllActiveRegistrationDetailsOfCourse(anyString())).thenReturn(List.of(new Registration("test_registration_id", "other.email@example.com", "other.email", fullCourse, Registration.Status.ACCEPTED)));
        assertThrows(CourseFullErrorException.class, () -> employeeServices.registerToACourse("valid.email@example.com", "fullCourseId"));
    }

    @DisplayName("Test Cancel Enrollment Throws Exception If No Registration Found")
    @Test
    void testCancelEnrollmentWhenRegistrationNotFoundThenThrowRegistrationNotFoundException() {
        when(registrationRepositoryMock.getRegistrationDetails("nonExistentRegistrationId")).thenReturn(null);
        assertThrows(RegistrationNotFoundException.class, () -> employeeServices.cancelEnrollment("nonExistentRegistrationId"));
    }

    @DisplayName("Test Cancel Enrollment Must Reject Cancellation If Course Is Confirmed For The Employee")
    @Test
    void testCancelEnrollmentWhenStatusConfirmedThenStatusCancelRejected() {
        Registration confirmedRegistration = new Registration("test_registration_id", "valid.email@example.com", "valid.email", new Course("test_course_id", "Course", "Instructor", new Date(), 1, 10), Registration.Status.CONFIRMED);
        Registration cancelRejectedRegistration = new Registration("test_registration_id", "valid.email@example.com", "valid.email", new Course("test_course_id", "Course", "Instructor", new Date(), 1, 10), Registration.Status.CANCEL_REJECTED);
        when(registrationRepositoryMock.getRegistrationDetails(anyString())).thenReturn(confirmedRegistration);
        when(registrationRepositoryMock.updateRegistrationStatus(List.of(confirmedRegistration), Registration.Status.CANCEL_REJECTED)).thenReturn(List.of(cancelRejectedRegistration));
        Registration updatedRegistration = employeeServices.cancelEnrollment("test_registration_id");
        assertEquals(Registration.Status.CANCEL_REJECTED, updatedRegistration.getEnrollmentStatus());
    }

    @DisplayName("Test Cancel Enrollment Must Cancel Registration For The Employee")
    @Test
    void testCancelEnrollment() {
        Registration confirmedRegistration = new Registration("test_registration_id", "valid.email@example.com", "valid.email", new Course("test_course_id", "Course", "Instructor", new Date(), 1, 10), Registration.Status.ACCEPTED);
        Registration cancelAcceptedRegistration = new Registration("test_registration_id", "valid.email@example.com", "valid.email", new Course("test_course_id", "Course", "Instructor", new Date(), 1, 10), Registration.Status.CANCEL_ACCEPTED);
        when(registrationRepositoryMock.getRegistrationDetails(anyString())).thenReturn(confirmedRegistration);
        when(registrationRepositoryMock.updateRegistrationStatus(List.of(confirmedRegistration), Registration.Status.CANCEL_ACCEPTED)).thenReturn(List.of(cancelAcceptedRegistration));
        Registration updatedRegistration = employeeServices.cancelEnrollment("test_registration_id");
        assertEquals(Registration.Status.CANCEL_ACCEPTED, updatedRegistration.getEnrollmentStatus());
    }

}
