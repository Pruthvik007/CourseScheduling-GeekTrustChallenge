package com.geektrust.backend.repositories;

import com.geektrust.backend.dtos.CourseDto;
import com.geektrust.backend.entities.Course;

import java.util.Optional;

public interface ICourseRepository {

    Course createCourseOffering(CourseDto course);

    Optional<Course> getCourse(String courseId);

}
