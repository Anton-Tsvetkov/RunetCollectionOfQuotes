package com.epam.laboratory;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.epam.laboratory.exceptions.NotANumberException;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


class WebPageLoader {

    private final static Logger LOGGER = Logger.getLogger(WebPageLoader.class);

    static String getQuoteByNumber(String number) {
        return loadQuoteByNumber(number.trim());
    }

    private static String loadQuoteByNumber(String number) {
        String desiredUrlAsString = new Parser().configureUrlForRequest(number);
        Pattern startQuoteCodePattern = Pattern.compile("og:description\"\scontent=\"(.+?)\"\s/>");
        String desiredQuote = "No more quotes";
        try {
            URL urlOfDesiredQuote = new URL(desiredUrlAsString);
            try (LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(urlOfDesiredQuote.openStream()))) {
                String lineRead = lineNumberReader.readLine();
                while (lineRead != null) {
                    Matcher startQuoteCodeMatcher = startQuoteCodePattern.matcher(lineRead);
                    while (startQuoteCodeMatcher.find()) {
                        desiredQuote = startQuoteCodeMatcher.group(1);
                    }
                    lineRead = lineNumberReader.readLine();
                }
            } catch (IOException e) {
                LOGGER.error(e);
            }
        } catch (MalformedURLException e) {
            LOGGER.error(e);
        }
        if (desiredQuote.equals("No more quotes")) {
            LOGGER.error("Incorrect input or quote doesn't exist", new NotANumberException("Input string not a number"));
        }
        LOGGER.info("loadQuoteByNumber get: " + desiredQuote);
        return desiredQuote;
    }
}
