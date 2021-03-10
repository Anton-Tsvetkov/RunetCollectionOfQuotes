package com.epam.laboratory;

public class RequestHandler {

    public String returnQuoteByNumber(String number){
        return new Parser()
                .normalizeQuote(
                        WebPageLoader.
                                getQuoteByNumber(number.trim()));
    }

}
