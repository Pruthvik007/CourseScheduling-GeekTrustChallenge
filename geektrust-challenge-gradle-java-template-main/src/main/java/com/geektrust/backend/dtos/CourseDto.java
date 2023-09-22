package com.geektrust.backend.dtos;

import java.util.Date;

public class CourseDto {

    private final String courseName;
    private final String instructor;
    private final Date startDate;
    private final Integer minimumEnrollments;
    private final Integer maximumEnrollments;

    public CourseDto(String courseName, String instructor, Date startDate, Integer minimumEnrollments, Integer maximumEnrollments) {
        this.courseName = courseName;
        this.instructor = instructor;
        this.startDate = startDate;
        this.minimumEnrollments = minimumEnrollments;
        this.maximumEnrollments = maximumEnrollments;
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

}
