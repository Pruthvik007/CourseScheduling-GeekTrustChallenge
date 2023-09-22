package com.geektrust.backend.repositories;

import com.geektrust.backend.dtos.RegistrationDto;
import com.geektrust.backend.entities.Registration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RegistrationRepository implements IRegistrationRepository {

    private final Map<String, Registration> registrationMap;

    public RegistrationRepository() {
        registrationMap = new HashMap<>();
    }

    public RegistrationRepository(Map<String, Registration> registrationMap) {
        this.registrationMap = registrationMap;
    }

    @Override
    public Registration createRegistration(RegistrationDto registrationDto) {
        Registration registration = new Registration(generateCourseRegistrationId(registrationDto), registrationDto.getEmployeeEmail(), registrationDto.getEmployeeName(), registrationDto.getCourse(), Registration.Status.ACCEPTED);
        registrationMap.put(registration.getRegistrationId(), registration);
        return registration;
    }

    @Override
    public List<Registration> updateRegistrationStatus(List<Registration> registrations, Registration.Status status) {
        List<Registration> updatedRegistrations = registrations.stream().map(registration -> new Registration(registration.getRegistrationId(), registration.getEmployeeEmail(), registration.getEmployeeName(), registration.getCourse(), status)).collect(Collectors.toList());
        updatedRegistrations.forEach(this::updateRegistration);
        return updatedRegistrations;
    }

    @Override
    public Registration getRegistrationDetails(String registrationId) {
        return registrationMap.getOrDefault(registrationId, null);
    }

    @Override
    public List<Registration> getAllActiveRegistrationDetailsOfCourse(String courseId) {
        return registrationMap.values().stream().filter(registration -> registration.getCourse().getCourseId().equals(courseId) && isRegistrationActive(registration)).collect(Collectors.toList());
    }

    private boolean isRegistrationActive(Registration registration) {
        return Registration.Status.ACCEPTED.equals(registration.getEnrollmentStatus()) || Registration.Status.CANCEL_REJECTED.equals(registration.getEnrollmentStatus());
    }

    private void updateRegistration(Registration registration) {
        registrationMap.put(registration.getRegistrationId(), registration);
    }

    private String generateCourseRegistrationId(RegistrationDto registrationDto) {
        return "REG-COURSE-" + registrationDto.getEmployeeName() + "-" + registrationDto.getCourse().getCourseName();
    }

}
