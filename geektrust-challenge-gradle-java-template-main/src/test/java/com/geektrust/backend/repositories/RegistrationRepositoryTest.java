package com.geektrust.backend.repositories;

import com.geektrust.backend.dtos.RegistrationDto;
import com.geektrust.backend.entities.Course;
import com.geektrust.backend.entities.Registration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationRepositoryTest {

    private RegistrationRepository registrationRepository;

    @BeforeEach
    void setUp() {
        Map<String, Registration> registrationMap = new HashMap<>();
        this.registrationRepository = new RegistrationRepository(registrationMap);
    }

    @DisplayName("Test Get Registration Details Empty If Not Present")
    @Test
    void testGetRegistrationReturnsEmptyIfNotPresent() {
        Registration registration = registrationRepository.getRegistrationDetails("test_id");
        assertNull(registration);
    }

    @DisplayName("Test Get Registration Must Return Registration Details")
    @Test
    void testGetRegistrationDetailsReturnsDetails() {
        Course course = new Course("course_id_1", "JAVA", "Albert", new Date(), 1, 5);
        RegistrationDto registrationToCreate = new RegistrationDto("Carl@mail.com", course);
        String registrationId = registrationRepository.createRegistration(registrationToCreate).getRegistrationId();
        Registration registrationDetailsAfterCreating = registrationRepository.getRegistrationDetails(registrationId);
        assertNotNull(registrationDetailsAfterCreating);
    }

    @DisplayName("Test Create Registration Must Add New Registration")
    @Test
    void testCreateRegistrationAddsRegistration() {
        Course course = new Course("test_course_id", "JAVA", "Albert", new Date(), 1, 5);
        RegistrationDto registrationDto = new RegistrationDto("Carl@mail.com", course);
        String expectedRegistrationId = "REG-COURSE-Carl-JAVA";
        String actualRegistrationId = registrationRepository.createRegistration(registrationDto).getRegistrationId();
        assertEquals(expectedRegistrationId, actualRegistrationId);
    }

    @DisplayName("Test Update Registration Status")
    @Test
    void testUpdateRegistrationUpdatesRegistrationDetails() {
        Course course = new Course("test_course_id", "JAVA", "Albert", new Date(), 1, 5);
        Registration createdRegistration = registrationRepository.createRegistration(new RegistrationDto("Carl@mail.com", course));
        Registration updatedRegistration = new Registration(createdRegistration.getRegistrationId(), "Carl@mail.com", "Carl", course, Registration.Status.CONFIRMED);
        Registration.Status expectedRegistrationStatusAfterUpdating = Registration.Status.CONFIRMED;
        Registration.Status actualRegistrationStatusAfterUpdating = registrationRepository.updateRegistrationStatus(List.of(updatedRegistration), expectedRegistrationStatusAfterUpdating).get(0).getEnrollmentStatus();
        assertEquals(expectedRegistrationStatusAfterUpdating, actualRegistrationStatusAfterUpdating);
    }

    @DisplayName("Test Get All Registration Details Must Return Empty If No Registrations Found For Given Course")
    @Test
    void testGetAllRegistrationsReturnsEmptyWhenNoRegistrationsFoundWithCourse() {
        List<Registration> registrations = registrationRepository.getAllActiveRegistrationDetailsOfCourse("JAVA");
        assertTrue(registrations.isEmpty());
    }

    @DisplayName("Test Get All Registrations Must Return Details Of All Registrations For Given Course")
    @Test
    void testGetAllRegistrationsMustReturnRegistrationsOfGivenCourse() {
        Course courseOne = new Course("test_course_id_one", "JAVA", "Albert", new Date(), 1, 5);
        RegistrationDto registrationOne = new RegistrationDto("Carl@mail.com", courseOne);
        Course courseTwo = new Course("test_course_id_two", "PYTHON", "Jeniffer", new Date(), 1, 5);
        RegistrationDto registrationTwo = new RegistrationDto("Mark@mail.com", courseTwo);
        registrationRepository.createRegistration(registrationOne);
        registrationRepository.createRegistration(registrationTwo);
        int expectedNumberOfRegistrations = 1;
        int actualNumberOfRegistrationsReturned = registrationRepository.getAllActiveRegistrationDetailsOfCourse("test_course_id_one").size();
        assertEquals(expectedNumberOfRegistrations, actualNumberOfRegistrationsReturned);
    }

}
