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


public class WebPageLoader {

    static private URL mainWebsiteUrl = null;

    static {
        try {
            mainWebsiteUrl = new URL("https://bash.im/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    final static Logger LOGGER = Logger.getLogger(WebPageLoader.class);


    public String getQuoteByNumber(String number) {
        return new Parser()
                .normalizeQuote(
                        loadQuoteByNumber(
                                number.trim()));
    }

    public static String getActualMaxAllowableValue() {
        String match = "000001";
        Pattern pattern = Pattern.compile("<article class=\"quote\" data-quote=\"(.+?)\">");
        try {
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(mainWebsiteUrl.openStream()));
            String string = reader.readLine();
            while (string != null) {
                Matcher matcher = pattern.matcher(string);
                if (matcher.find()) {
                    match = matcher.group(1);
                    break;
                }
                string = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            LOGGER.error("getActualMaxAllowableValue failed", e);
        }
        LOGGER.info("getActualMaxAllowableValue get: " + match);
        return match;
    }

    static String loadQuoteByNumber(String number) {
        String desiredUrlAsString = new Parser().configureUrlForRequest(number);
        Pattern startQuoteCodePattern = Pattern.compile("og:description\"\scontent=\"(.+?)\"\s/>");
        String desiredQuote = "No more quotes";
        try {
            URL urlOfDesiredQuote = new URL(desiredUrlAsString);
            try {
                LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(urlOfDesiredQuote.openStream()));
                String lineRead = lineNumberReader.readLine();
                while (lineRead != null) {
                    Matcher startQuoteCodeMatcher = startQuoteCodePattern.matcher(lineRead);
                    while (startQuoteCodeMatcher.find()) {
                        desiredQuote = startQuoteCodeMatcher.group(1);
                    }
                    lineRead = lineNumberReader.readLine();
                }
                lineNumberReader.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        } catch (MalformedURLException e) {
            LOGGER.error(e);
        }
        if(desiredQuote.equals("No more quotes")){
            LOGGER.error("Incorrect input or quote doesn't exist", new NotANumberException("Input string not a number"));
        }
        LOGGER.info("loadQuoteByNumber get: " + desiredQuote);
        return desiredQuote;
    }
}
