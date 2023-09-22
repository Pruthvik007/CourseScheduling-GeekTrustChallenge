package com.geektrust.backend.repositories;

import com.geektrust.backend.dtos.RegistrationDto;
import com.geektrust.backend.entities.Registration;

import java.util.List;

public interface IRegistrationRepository {

    Registration createRegistration(RegistrationDto registrationDto);

    List<Registration> updateRegistrationStatus(List<Registration> registrations, Registration.Status status);

    Registration getRegistrationDetails(String registrationId);

    List<Registration> getAllActiveRegistrationDetailsOfCourse(String courseId);

}
