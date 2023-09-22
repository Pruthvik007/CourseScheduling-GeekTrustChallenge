package com.geektrust.backend.repositories;

import com.geektrust.backend.dtos.CourseDto;
import com.geektrust.backend.entities.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Course Repository Tests")
class CourseRepositoryTest {

    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        Map<String, Course> courseMap = new HashMap<>();
        courseRepository = new CourseRepository(courseMap);
    }

    @DisplayName("Test Create Course")
    @Test
    void testCreateCourseOfferingCreatesCourse() {
        CourseDto courseDto = new CourseDto("Java", "Dwayne John", new Date(), 10, 25);
        courseRepository.createCourseOffering(courseDto);
        String expectedCourseId = "OFFERING-Java-Dwayne John";
        assertTrue(courseRepository.getCourse(expectedCourseId).isPresent());
    }

    @DisplayName("Test Course Id Generation During Course Creation")
    @Test
    void testCreateCourseOfferingWhenCourseIsPassedThenCourseIdIsSet() {
        CourseDto courseDto = new CourseDto("Java", "John Doe", new Date(), 10, 20);
        String expectedCourseId = "OFFERING-Java-John Doe";
        String actualCourseId = courseRepository.createCourseOffering(courseDto).getCourseId();
        assertEquals(expectedCourseId, actualCourseId);
    }

}
