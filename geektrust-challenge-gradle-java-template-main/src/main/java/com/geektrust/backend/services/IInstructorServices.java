package com.geektrust.backend.services;

import com.geektrust.backend.dtos.CourseDto;
import com.geektrust.backend.entities.Course;
import com.geektrust.backend.entities.Registration;

import java.util.List;

public interface IInstructorServices {

    Course addCourseOffering(CourseDto course);

    List<Registration> allotCourse(String courseOfferingId);

}
