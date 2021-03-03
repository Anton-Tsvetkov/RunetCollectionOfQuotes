package com.epam.laboratory;

import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    final static Logger LOGGER = Logger.getLogger(WebPageLoader.class);
    static private URL mainWebsiteUrl = null;
    static {
        try {
            mainWebsiteUrl = new URL("https://bash.im/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    static String replaceClassicQuotes(String quote){
        Pattern classicalQuotesPattern = Pattern.compile("&quot;");
        Matcher classicalQuotesMatcher = classicalQuotesPattern.matcher(quote);
        quote = classicalQuotesMatcher.replaceAll("\"");
        if(quote.contains("&quot;")) LOGGER.error("replaceClassicQuotes does not work correctly");
        return quote;
    }

    static String replaceLeftSingleTreeQuotes(String quote){
        Pattern leftSingleTreeQuotesPattern = Pattern.compile("&lt;");
        Matcher leftSingleTreeQuotesMatcher = leftSingleTreeQuotesPattern.matcher(quote);
        quote = leftSingleTreeQuotesMatcher.replaceAll("<");
        if(quote.contains("&lt;")) LOGGER.error("replaceLeftSingleTreeQuotes does not work correctly");
        return quote;
    }

    static String replaceRightSingleTreeQuotes(String quote){
        Pattern rightSingleTreeQuotesPattern = Pattern.compile("&gt;");
        Matcher rightSingleTreeQuotesMatcher = rightSingleTreeQuotesPattern.matcher(quote);
        quote = rightSingleTreeQuotesMatcher.replaceAll(">");
        if(quote.contains("&gt;")) LOGGER.error("replaceRightSingleTreeQuotes does not work correctly");
        return quote;
    }

    static String replaceAllQuotes(String quote){
        quote = replaceClassicQuotes(quote);
        quote = replaceLeftSingleTreeQuotes(quote);
        quote = replaceRightSingleTreeQuotes(quote);
        return quote;
    }

    static String replaceAllNewLines(String quote){
        Pattern newLinePattern = Pattern.compile("&#13;&#10;");
        Matcher newLineMatcher = newLinePattern.matcher(quote);
        quote = newLineMatcher.replaceAll("\n");
        if(quote.contains("&#13;&#10;")) LOGGER.error("replaceAllNewLines does not work correctly");
        return quote;
    }

    public String normalizeQuote(String quote){
        quote = replaceAllQuotes(quote);
        quote = replaceAllNewLines(quote);
        return quote;
    }

    public String configureUrlForRequest(String number){
        return mainWebsiteUrl.toString() + "quote/" + number;
    }



}
