package com.geektrust.backend;

import com.geektrust.backend.appconfig.AppConfiguration;
import com.geektrust.backend.commands.CommandInvoker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        run(commandLineArgs);
    }

    public static void run(List<String> commandLineArgs) {
        AppConfiguration applicationConfig = new AppConfiguration();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        String inputFile = commandLineArgs.get(0);
        commandLineArgs.remove(0);
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line = reader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.asList(line.split(" "));
                commandInvoker.executeCommand(tokens.get(0), tokens.stream().skip(1).collect(Collectors.toList()));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
