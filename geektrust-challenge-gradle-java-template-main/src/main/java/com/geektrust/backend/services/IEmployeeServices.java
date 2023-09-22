package com.geektrust.backend.services;

import com.geektrust.backend.entities.Registration;
import com.geektrust.backend.exceptions.CourseFullErrorException;
import com.geektrust.backend.exceptions.CourseNotFoundException;
import com.geektrust.backend.exceptions.InputDataErrorException;
import com.geektrust.backend.exceptions.RegistrationNotFoundException;

public interface IEmployeeServices {

    Registration registerToACourse(String emailAddress, String courseOfferingId) throws InputDataErrorException, CourseNotFoundException, CourseFullErrorException;

    Registration cancelEnrollment(String courseRegistrationId) throws RegistrationNotFoundException;

}
