package com.geektrust.backend;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@DisplayName("App Tests")
class AppTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Integration Test One")
    void runTestOne() {
        List<String> arguments = new ArrayList<>(List.of("sample_input/input1.txt"));
        String expectedOutput = "OFFERING-DATASCIENCE-BOB\n" +
                "REG-COURSE-WOO-DATASCIENCE ACCEPTED\n" +
                "REG-COURSE-ANDY-DATASCIENCE ACCEPTED\n" +
                "REG-COURSE-ANDY-DATASCIENCE ANDY@GMAIL.COM OFFERING-DATASCIENCE-BOB DATASCIENCE BOB 05062022 CONFIRMED\n" +
                "REG-COURSE-WOO-DATASCIENCE WOO@GMAIL.COM OFFERING-DATASCIENCE-BOB DATASCIENCE BOB 05062022 CONFIRMED";
        App.run(arguments);
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Integration Test Two")
    void runTestTwo() {
        List<String> arguments = new ArrayList<>(List.of("sample_input/input2.txt"));
        String expectedOutput = "OFFERING-PYTHON-JOHN\n" +
                "REG-COURSE-WOO-PYTHON ACCEPTED\n" +
                "REG-COURSE-ANDY-PYTHON ACCEPTED\n" +
                "REG-COURSE-BOBY-PYTHON ACCEPTED\n" +
                "REG-COURSE-BOBY-PYTHON CANCEL_ACCEPTED\n" +
                "REG-COURSE-ANDY-PYTHON ANDY@GMAIL.COM OFFERING-PYTHON-JOHN PYTHON JOHN 05062022 CONFIRMED\n" +
                "REG-COURSE-WOO-PYTHON WOO@GMAIL.COM OFFERING-PYTHON-JOHN PYTHON JOHN 05062022 CONFIRMED";
        App.run(arguments);
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Integration Test Three")
    void runTestThree() {
        List<String> arguments = new ArrayList<>(List.of("sample_input/input3.txt"));
        String expectedOutput = "OFFERING-DATASCIENCE-BOB\n" +
                "REG-COURSE-ANDY-DATASCIENCE ACCEPTED\n" +
                "REG-COURSE-WOO-DATASCIENCE ACCEPTED\n" +
                "COURSE_FULL_ERROR\n" +
                "REG-COURSE-ANDY-DATASCIENCE ANDY@GMAIL.COM OFFERING-DATASCIENCE-BOB DATASCIENCE BOB 05062022 CONFIRMED\n" +
                "REG-COURSE-WOO-DATASCIENCE WOO@GMAIL.COM OFFERING-DATASCIENCE-BOB DATASCIENCE BOB 05062022 CONFIRMED";
        App.run(arguments);
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Integration Test Four")
    void runTestFour() {
        List<String> arguments = new ArrayList<>(List.of("sample_input/input4.txt"));
        String expectedOutput = "OFFERING-DATASCIENCE-BOB\n" +
                "REG-COURSE-ANDY-DATASCIENCE ACCEPTED\n" +
                "REG-COURSE-WOO-DATASCIENCE ACCEPTED\n" +
                "INPUT_DATA_ERROR\n" +
                "REG-COURSE-ANDY-DATASCIENCE ANDY@GMAIL.COM OFFERING-DATASCIENCE-BOB DATASCIENCE BOB 05062022 COURSE_CANCELED\n" +
                "REG-COURSE-WOO-DATASCIENCE WOO@GMAIL.COM OFFERING-DATASCIENCE-BOB DATASCIENCE BOB 05062022 COURSE_CANCELED";
        App.run(arguments);
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

}
