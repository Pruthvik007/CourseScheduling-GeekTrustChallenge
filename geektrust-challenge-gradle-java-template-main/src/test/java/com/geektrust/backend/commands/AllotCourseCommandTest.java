package com.geektrust.backend.commands;

import com.geektrust.backend.constants.Constants;
import com.geektrust.backend.entities.Course;
import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.CourseNotFoundException;
import com.geektrust.backend.services.IInstructorServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("AllotCourseCommand Tests")
@ExtendWith(MockitoExtension.class)
class AllotCourseCommandTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @InjectMocks
    private AllotCourseCommand allotCourseCommand;
    @Mock
    private IInstructorServices instructorServicesMock;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @DisplayName("Test AllotCourse Logs Registration Details")
    @Test
    void testAllotCourseCommandLogsRegistrationDetails() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.COURSE_DATE_FORMAT);
        String courseId = "course_id";
        String expectedOutput = "registration_id_a employeeone@mail.com course_id courseName instructorName " + dateFormat.format(new Date()) + " ACCEPTED\n" +
                "registration_id_b employeetwo@mail.com course_id courseName instructorName " + dateFormat.format(new Date()) + " ACCEPTED";
        Course course = new Course(courseId, "courseName", "instructorName", new Date(), 1, 5);
        Registration registrationOne = new Registration("registration_id_a", "employeeone@mail.com", "one", course, Registration.Status.ACCEPTED);
        Registration registrationTwo = new Registration("registration_id_b", "employeetwo@mail.com", "two", course, Registration.Status.ACCEPTED);
        List<Registration> registrations = List.of(registrationOne, registrationTwo);
        when(instructorServicesMock.allotCourse(courseId)).thenReturn(registrations);
        allotCourseCommand.execute(List.of(courseId));
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(instructorServicesMock, times(1)).allotCourse(courseId);
    }

    @DisplayName("Test Allot Course Command With No Arguments Logs Error Message")
    @Test
    void testAllotCourseCommandWithNoArgumentsLogsErrorMessage() {
        String expectedOutput = Constants.MESSAGE_OF_INPUT_DATA_ERROR_EXCEPTION.trim();
        allotCourseCommand.execute(null);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Test Allot Course Command When Course Not Found Logs Error Message")
    @Test
    void testAllotCourseCommandLogsMessageWhenNoCourseFound() {
        String invalidOrNonExistentCourseId = "course_id";
        String expectedOutput = Constants.MESSAGE_OF_COURSE_NOT_FOUND_EXCEPTION.trim();
        doThrow(new CourseNotFoundException(expectedOutput)).when(instructorServicesMock).allotCourse(invalidOrNonExistentCourseId);
        allotCourseCommand.execute(List.of(invalidOrNonExistentCourseId));
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(instructorServicesMock, times(1)).allotCourse(invalidOrNonExistentCourseId);
    }

}
