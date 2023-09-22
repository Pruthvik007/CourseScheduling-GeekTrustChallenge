package com.geektrust.backend.services;

import com.geektrust.backend.dtos.CourseDto;
import com.geektrust.backend.entities.Course;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.CourseNotFoundException;
import com.geektrust.backend.repositories.ICourseRepository;
import com.geektrust.backend.repositories.IRegistrationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Instructor Services Test")
@ExtendWith(MockitoExtension.class)
class InstructorServicesTest {

    @InjectMocks
    private InstructorServices instructorServices;
    @Mock
    private ICourseRepository courseRepositoryMock;
    @Mock
    private IRegistrationRepository registrationRepositoryMock;

    @Test
    void testAddCourseOffering() {
        instructorServices.addCourseOffering(new CourseDto("JAVA", "Sam", new Date(), 1, 1));
        verify(courseRepositoryMock, times(1)).createCourseOffering(any(CourseDto.class));
    }

    @DisplayName("Test Allot Course Must Update Registration Status of Each Registration To Confirmed")
    @Test
    void testAllotCourseUpdatesRegistrationStatusOfEachRegistration() {
        String courseId = "course_id";
        Course course = new Course(courseId, "JAVA", "Sam", new Date(), 1, 5);
        when(courseRepositoryMock.getCourse(courseId)).thenReturn(Optional.of(course));
        Registration registrationOfEmployeeOne = new Registration("registration_id_1", "employeeone@mail.com", "one", course, Registration.Status.ACCEPTED);
        Registration updatedRegistration = new Registration("registration_id_1", "employeetwo@mail.com", "two", course, Registration.Status.CONFIRMED);
        when(registrationRepositoryMock.getAllActiveRegistrationDetailsOfCourse(courseId)).thenReturn(List.of(registrationOfEmployeeOne));
        when(registrationRepositoryMock.updateRegistrationStatus(List.of(registrationOfEmployeeOne), Registration.Status.CONFIRMED)).thenReturn(List.of(updatedRegistration));
        Registration.Status expectedRegistrationStatus = Registration.Status.CONFIRMED;
        Registration.Status actualRegistrationStatus = instructorServices.allotCourse(courseId).get(0).getEnrollmentStatus();
        assertEquals(expectedRegistrationStatus, actualRegistrationStatus);
    }

    @DisplayName("Test Allot Course Must Return Registrations With Status COURSE_CANCELLED If Minimum Employees Are Not Available")
    @Test
    void testAllotCourseMustNotUpdateRegistrationStatusIfMinimumEmployeesNotReached() {
        String courseId = "course_id";
        Course course = new Course(courseId, "JAVA", "Sam", new Date(), 5, 5);
        when(courseRepositoryMock.getCourse(courseId)).thenReturn(Optional.of(course));
        Registration registrationOfEmployeeOne = new Registration("registration_id", "employeeone@mail.com", "one", course, Registration.Status.ACCEPTED);
        when(registrationRepositoryMock.getAllActiveRegistrationDetailsOfCourse(courseId)).thenReturn(List.of(registrationOfEmployeeOne));
        Registration updatedRegistration = new Registration("registration_id", "employeeone@mail.com", "one", course, Registration.Status.COURSE_CANCELED);
        when(registrationRepositoryMock.updateRegistrationStatus(List.of(registrationOfEmployeeOne), Registration.Status.COURSE_CANCELED)).thenReturn(List.of(updatedRegistration));
        Registration.Status expectedRegistrationStatus = Registration.Status.COURSE_CANCELED;
        Registration.Status actualRegistrationStatus = instructorServices.allotCourse(courseId).get(0).getEnrollmentStatus();
        assertEquals(expectedRegistrationStatus, actualRegistrationStatus);
    }

    @DisplayName("Test Allot Course Throws Course Not Found If Given Course Is Not Found")
    @Test
    void testAllotCourseWithInvalidCourseId() {
        String courseId = "non_existent_course_id";
        assertThrows(CourseNotFoundException.class, () -> instructorServices.allotCourse(courseId));
    }

    @DisplayName("Test Allot Course Must Return Registrations In Ascending Order Of Registration Id")
    @Test
    void testAllotCourseUpdatesStatusAndReturnsRegistrationsInAscendingOrderOfRegistrationId() {
        String courseId = "course_id";
        Course course = new Course(courseId, "JAVA", "Sam", new Date(), 1, 5);
        when(courseRepositoryMock.getCourse(courseId)).thenReturn(Optional.of(course));
        Registration registrationOfEmployeeOne = new Registration("registration_id_b", "employeeone@mail.com", "one", course, Registration.Status.ACCEPTED);
        Registration registrationOfEmployeeTwo = new Registration("registration_id_a", "employeetwo@mail.com", "two", course, Registration.Status.ACCEPTED);
        when(registrationRepositoryMock.getAllActiveRegistrationDetailsOfCourse(courseId)).thenReturn(List.of(registrationOfEmployeeOne, registrationOfEmployeeTwo));
        Registration updatedRegistrationOfEmployeeOne = new Registration("registration_id_b", "employeeone@mail.com", "one", course, Registration.Status.CONFIRMED);
        Registration updatedRegistrationOfEmployeeTwo = new Registration("registration_id_a", "employeetwo@mail.com", "two", course, Registration.Status.CONFIRMED);
        when(registrationRepositoryMock.updateRegistrationStatus(List.of(registrationOfEmployeeOne, registrationOfEmployeeTwo), Registration.Status.CONFIRMED)).thenReturn(List.of(updatedRegistrationOfEmployeeTwo, updatedRegistrationOfEmployeeOne));
        List<Registration> expectedRegistrations = List.of(updatedRegistrationOfEmployeeTwo, updatedRegistrationOfEmployeeOne);
        List<Registration> actualRegistrations = instructorServices.allotCourse(courseId);
        assertEquals(expectedRegistrations.toString(), actualRegistrations.toString());
    }

}
