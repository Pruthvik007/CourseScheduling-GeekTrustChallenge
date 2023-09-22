package com.geektrust.backend.commands;

import com.geektrust.backend.constants.Constants;
import com.geektrust.backend.entities.Course;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.RegistrationNotFoundException;
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

@DisplayName("CancelEnrollmentCommand Tests")
@ExtendWith(MockitoExtension.class)
class CancelEnrollmentCommandTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Mock
    private IEmployeeServices employeeServicesMock;
    @InjectMocks
    private CancelEnrollmentCommand cancelEnrollmentCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @DisplayName("Test Cancel Enrollment Command With No Arguments Logs Error Message")
    @Test
    void testCancelEnrollmentWithNoArgumentsLogsErrorMessage() {
        String expectedOutput = Constants.MESSAGE_OF_INPUT_DATA_ERROR_EXCEPTION.trim();
        cancelEnrollmentCommand.execute(null);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Test Cancel Enrollment Command When Registration Not Found Logs Error Message")
    @Test
    void testCancelEnrollmentLogsMessageWhenNoRegistrationFound() {
        String invalidOrNonExistentRegistrationId = "registration_id";
        String expectedOutput = Constants.MESSAGE_OF_REGISTRATION_NOT_FOUND_EXCEPTION.trim();
        doThrow(new RegistrationNotFoundException(expectedOutput)).when(employeeServicesMock).cancelEnrollment(invalidOrNonExistentRegistrationId);
        cancelEnrollmentCommand.execute(List.of(invalidOrNonExistentRegistrationId));
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(employeeServicesMock, times(1)).cancelEnrollment(invalidOrNonExistentRegistrationId);
    }

    @DisplayName("Test Cancel Enrollment Logs Updated Status After Cancellation")
    @Test
    void testCancelEnrollmentCancellationLogsStatus() {
        String courseRegistrationId = "course_registration_id";
        String expectedOutput = courseRegistrationId + " " + Registration.Status.CANCEL_ACCEPTED;
        Course course = new Course("test_course_id", "JAVA", "Albert", new Date(), 1, 5);
        Registration registration = new Registration(courseRegistrationId, "Carl@mail.com", "Carl", course, Registration.Status.CANCEL_ACCEPTED);
        when(employeeServicesMock.cancelEnrollment(courseRegistrationId)).thenReturn(registration);
        cancelEnrollmentCommand.execute(List.of(courseRegistrationId));
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(employeeServicesMock, times(1)).cancelEnrollment(courseRegistrationId);
    }

}
