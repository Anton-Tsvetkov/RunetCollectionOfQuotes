package com.epam.laboratory;

import com.epam.laboratory.exceptions.NotANumberException;
import com.epam.laboratory.exceptions.NumberOutOfRangeException;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputChecker {

    final static Logger LOGGER = Logger.getLogger(WebPageLoader.class);
    final static int AMOUNT_NUMERICS_IN_NUMBER = 6;
    final static long MAX_ALLOWABLE_VALUE = Long.parseLong(WebPageLoader.getActualMaxAllowableValue());


    boolean numberContainsJustNumerics(String number) throws NotANumberException {
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

    boolean numberIncludedInRange(String number) throws NumberOutOfRangeException {
        try {
            if (Long.parseLong(number) > MAX_ALLOWABLE_VALUE || number.length() < AMOUNT_NUMERICS_IN_NUMBER) {
                throw new NumberOutOfRangeException("Input number not included in the range from 000001 to 465000 inclusive");
            }
            LOGGER.info("Input number (" + number + ") included in range");
            return true;
        } catch (NumberOutOfRangeException exception) {
            LOGGER.error(exception);
        }
        return false;
    }

    public boolean numberIsCorrect(String number) throws NotANumberException, NumberOutOfRangeException {
        number = number.replaceAll(" ", "");
        return numberIncludedInRange(number) && numberContainsJustNumerics(number);
    }

}
