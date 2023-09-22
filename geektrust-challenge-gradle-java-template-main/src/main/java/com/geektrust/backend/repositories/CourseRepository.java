package com.geektrust.backend.repositories;

import com.geektrust.backend.dtos.CourseDto;
import com.geektrust.backend.entities.Course;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CourseRepository implements ICourseRepository {

    private final Map<String, Course> courseMap;

    public CourseRepository() {
        courseMap = new HashMap<>();
    }

    public CourseRepository(Map<String, Course> courseMap) {
        this.courseMap = courseMap;
    }

    @Override
    public Course createCourseOffering(CourseDto courseDto) {
        Course course = new Course(generateCourseId(courseDto), courseDto);
        courseMap.put(course.getCourseId(), course);
        return course;
    }

    private String generateCourseId(CourseDto courseDto) {
        return "OFFERING-" + courseDto.getCourseName() + "-" + courseDto.getInstructor();
    }

    @Override
    public Optional<Course> getCourse(String courseId) {
        return Optional.ofNullable(courseMap.get(courseId));
    }

}
