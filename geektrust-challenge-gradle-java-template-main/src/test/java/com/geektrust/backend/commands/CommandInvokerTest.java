package com.geektrust.backend.commands;

import com.geektrust.backend.exceptions.NoSuchCommandException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Command Invoker Tests")
@ExtendWith(MockitoExtension.class)
class CommandInvokerTest {

    @InjectMocks
    private CommandInvoker commandInvoker;

    @DisplayName("Test Command Invoker Raises Exception If Command Provided Is Invalid")
    @Test
    void testCommandInvokerWhenCommandIsInvalid() {
        assertThrows(NoSuchCommandException.class, () -> commandInvoker.executeCommand("InvalidCommand", null));
    }

}
