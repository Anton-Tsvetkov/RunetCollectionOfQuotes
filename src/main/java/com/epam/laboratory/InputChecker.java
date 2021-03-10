package com.epam.laboratory;

import com.epam.laboratory.exceptions.NotANumberException;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InputChecker {

    private final static Logger LOGGER = Logger.getLogger(WebPageLoader.class);

    boolean numberIsCorrect(String number) {
        Pattern notNumericsPattern = Pattern.compile("[^0-9]");
        Matcher notNumericsMatcher = notNumericsPattern.matcher(number);
        try {
            if (notNumericsMatcher.find()) {
                throw new NotANumberException("Input string not a number");
            }
            LOGGER.info("Input number (" + number + ") contains just numerics");
            return true;
        } catch (NotANumberException exception) {
            LOGGER.error(exception);
        }
        return false;
    }

}
