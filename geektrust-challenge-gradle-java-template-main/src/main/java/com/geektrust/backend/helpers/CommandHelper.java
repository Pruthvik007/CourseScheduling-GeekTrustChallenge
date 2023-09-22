package com.geektrust.backend.helpers;

import com.geektrust.backend.constants.Constants;
import com.geektrust.backend.exceptions.InputDataErrorException;

import java.util.List;

public class CommandHelper {

    private CommandHelper() {

    }

    public static void validateArguments(List<String> arguments, int requiredNumberOfArguments) {
        if (arguments == null || arguments.size() != requiredNumberOfArguments) {
            throw new InputDataErrorException(Constants.MESSAGE_OF_INPUT_DATA_ERROR_EXCEPTION);
        }
    }

}
