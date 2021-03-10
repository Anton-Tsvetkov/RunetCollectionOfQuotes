package com.epam.laboratory;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Parser {

    private final static String PATH_TO_PROPERTIES = "src/main/resources/URL.properties";

    private final static Logger LOGGER = Logger.getLogger(WebPageLoader.class);

    private static String getUrlForQuoteFromProperties() {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        String urlForQuote = "";
        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            properties.load(fileInputStream);
            urlForQuote = properties.getProperty("runetUrlForQuote");
        } catch (IOException ex) {
            LOGGER.error("Error in file or path: " + PATH_TO_PROPERTIES + ex.getMessage());
            ex.printStackTrace();
        }
        return urlForQuote;
    }

    private static String replaceClassicQuotes(String quote) {
        Pattern classicalQuotesPattern = Pattern.compile("&quot;");
        Matcher classicalQuotesMatcher = classicalQuotesPattern.matcher(quote);
        quote = classicalQuotesMatcher.replaceAll("\"");
        if (quote.contains("&quot;")) LOGGER.error("replaceClassicQuotes does not work correctly");
        return quote;
    }

    private static String replaceLeftSingleTreeQuotes(String quote) {
        Pattern leftSingleTreeQuotesPattern = Pattern.compile("&lt;");
        Matcher leftSingleTreeQuotesMatcher = leftSingleTreeQuotesPattern.matcher(quote);
        quote = leftSingleTreeQuotesMatcher.replaceAll("<");
        if (quote.contains("&lt;")) LOGGER.error("replaceLeftSingleTreeQuotes does not work correctly");
        return quote;
    }

    private static String replaceRightSingleTreeQuotes(String quote) {
        Pattern rightSingleTreeQuotesPattern = Pattern.compile("&gt;");
        Matcher rightSingleTreeQuotesMatcher = rightSingleTreeQuotesPattern.matcher(quote);
        quote = rightSingleTreeQuotesMatcher.replaceAll(">");
        if (quote.contains("&gt;")) LOGGER.error("replaceRightSingleTreeQuotes does not work correctly");
        return quote;
    }

    private static String replaceAllQuotes(String quote) {
        quote = replaceClassicQuotes(quote);
        quote = replaceLeftSingleTreeQuotes(quote);
        quote = replaceRightSingleTreeQuotes(quote);
        return quote;
    }

    private static String replaceAllNewLines(String quote) {
        Pattern newLinePattern = Pattern.compile("&#13;&#10;");
        Matcher newLineMatcher = newLinePattern.matcher(quote);
        quote = newLineMatcher.replaceAll("\n");
        if (quote.contains("&#13;&#10;")) LOGGER.error("replaceAllNewLines does not work correctly");
        return quote;
    }

    String normalizeQuote(String quote) {
        quote = replaceAllQuotes(quote);
        quote = replaceAllNewLines(quote);
        return quote;
    }

    String configureUrlForRequest(String number) {
        return getUrlForQuoteFromProperties() + number;
    }


}
