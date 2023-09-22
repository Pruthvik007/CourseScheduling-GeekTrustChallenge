package com.geektrust.backend.entities;

import com.geektrust.backend.dtos.CourseDto;

import java.util.Date;

public class Course {

    private final String courseId;
    private final String courseName;
    private final String instructor;
    private final Date startDate;
    private final Integer minimumEnrollments;
    private final Integer maximumEnrollments;

    public Course(String courseId, String courseName, String instructorName, Date startDate, Integer minimumEmployees,
                  Integer maximumEmployees) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructorName;
        this.startDate = startDate;
        this.minimumEnrollments = minimumEmployees;
        this.maximumEnrollments = maximumEmployees;
    }

    public Course(String courseId, CourseDto courseDto) {
        this.courseId = courseId;
        this.courseName = courseDto.getCourseName();
        this.instructor = courseDto.getInstructor();
        this.startDate = courseDto.getStartDate();
        this.minimumEnrollments = courseDto.getMinimumEnrollments();
        this.maximumEnrollments = courseDto.getMaximumEnrollments();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Integer getMinimumEnrollments() {
        return minimumEnrollments;
    }

    public Integer getMaximumEnrollments() {
        return maximumEnrollments;
    }

    @Override
    public String toString() {
        return "Course [courseId=" + courseId + ", courseName=" + courseName + ", instructor=" + instructor
                + ", startDate=" + startDate + ", minimumEnrollments=" + minimumEnrollments + ", maximumEnrollments="
                + maximumEnrollments + "]";
    }

}
