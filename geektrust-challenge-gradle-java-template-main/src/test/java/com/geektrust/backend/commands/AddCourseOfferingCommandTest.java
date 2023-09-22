package com.geektrust.backend.commands;

import com.geektrust.backend.constants.Constants;
import com.geektrust.backend.dtos.CourseDto;
import com.geektrust.backend.entities.Course;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("AddCourseOfferingCommand Tests")
@ExtendWith(MockitoExtension.class)
class AddCourseOfferingCommandTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @InjectMocks
    private AddCourseOfferingCommand addCourseOfferingCommand;
    @Mock
    private IInstructorServices instructorServicesMock;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @DisplayName("Test Add Course When No Arguments Are Provided Logs Error Message")
    @Test
    void testAddCourseOfferingCommandWithNoArguments() {
        String expectedOutput = Constants.MESSAGE_OF_INPUT_DATA_ERROR_EXCEPTION.trim();
        addCourseOfferingCommand.execute(null);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Test Add Course Returns and Logs Course Id After Creation")
    @Test
    void testAddCourseOfferingCommandWithAfterSuccessfulExecution() {
        String testCourseId = "test_course_id";
        String courseName = "JAVA";
        String instructorName = "MARUTHI";
        String date = "02122023";
        int minimumEmployees = 1;
        int maximumEmployees = 5;
        Course course = new Course(testCourseId, courseName, instructorName, new Date(), minimumEmployees, maximumEmployees);
        when(instructorServicesMock.addCourseOffering(any(CourseDto.class))).thenReturn(course);
        addCourseOfferingCommand.execute(List.of(courseName, instructorName, date, String.valueOf(minimumEmployees), String.valueOf(maximumEmployees)));
        assertEquals(testCourseId, outputStreamCaptor.toString().trim());
    }

}
