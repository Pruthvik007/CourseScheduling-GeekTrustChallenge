package com.geektrust.backend.commands;

import com.geektrust.backend.constants.Constants;
import com.geektrust.backend.entities.Course;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.CourseNotFoundException;
import com.geektrust.backend.services.IEmployeeServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("RegisterToACourseCommand Tests")
@ExtendWith(MockitoExtension.class)
class RegisterToACourseCommandTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @InjectMocks
    private RegisterToACourseCommand registerToACourseCommand;
    @Mock
    private IEmployeeServices employeeServicesMock;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @DisplayName("Test Register To A Course By Employee Logs Details Of Registration")
    @Test
    void testRegisterToACourseLogsRegistrationDetails() {
        String emailAddress = "test@mail.com";
        String courseId = "course_id";
        String registrationId = "registration_id";
        String expectedOutput = registrationId + " " + Registration.Status.ACCEPTED;
        Course course = new Course("test_course_id", "JAVA", "Albert", new Date(), 1, 5);
        Registration registration = new Registration(registrationId, "Carl@mail.com", "Carl", course, Registration.Status.ACCEPTED);
        when(employeeServicesMock.registerToACourse(emailAddress, courseId)).thenReturn(registration);
        registerToACourseCommand.execute(List.of(emailAddress, courseId));
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(employeeServicesMock, times(1)).registerToACourse(emailAddress, courseId);
    }

    @DisplayName("Test Register To A Course Logs Error Message When No Arguments Are Provided")
    @Test
    void testRegisterToCourseWithNoArgumentsLogsErrorMessage() {
        String expectedOutput = Constants.MESSAGE_OF_INPUT_DATA_ERROR_EXCEPTION.trim();
        registerToACourseCommand.execute(null);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Test Register To A Course Logs Error Message When No Course Found")
    @Test
    void testRegisterToCourseLogsMessageWhenNoCourseFound() {
        String invalidOrNonExistentCourseId = "course_id";
        String emailAddress = "test@mail.com";
        String expectedOutput = Constants.MESSAGE_OF_COURSE_NOT_FOUND_EXCEPTION.trim();
        doThrow(new CourseNotFoundException(expectedOutput)).when(employeeServicesMock).registerToACourse(emailAddress, invalidOrNonExistentCourseId);
        registerToACourseCommand.execute(List.of(emailAddress, invalidOrNonExistentCourseId));
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(employeeServicesMock, times(1)).registerToACourse(emailAddress, invalidOrNonExistentCourseId);
    }

}
