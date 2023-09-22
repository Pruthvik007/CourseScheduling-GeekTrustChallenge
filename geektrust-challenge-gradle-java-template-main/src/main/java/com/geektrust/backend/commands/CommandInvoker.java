package com.geektrust.backend.commands;

import com.geektrust.backend.exceptions.NoSuchCommandException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandInvoker {

    private static final Map<String, ICommand> commandMap = new HashMap<>();

    public void register(String commandName, ICommand command) {
        commandMap.put(commandName, command);
    }

    public void executeCommand(String commandName, List<String> tokens) throws NoSuchCommandException {
        ICommand command = get(commandName);
        if (command == null) {
            throw new NoSuchCommandException("No Such Command Exists");
        }
        command.execute(tokens);
    }

    private ICommand get(String commandName) {
        return commandMap.get(commandName);
    }

}
