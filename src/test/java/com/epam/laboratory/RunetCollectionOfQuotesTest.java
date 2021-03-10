package com.epam.laboratory;

import org.junit.Assert;
import org.junit.Test;

public class RunetCollectionOfQuotesTest {

    final InputChecker INPUT_CHECKER = new InputChecker();
    final Parser PARSER = new Parser();
    final RequestHandler REQUEST_HANDLER = new RequestHandler();

    final String CORRECT_NUMBER = "463765";
    final String UNTREATED_QUOTE = "&quot;Оксфордский словарь не смог описать 2020 год одним словом&quot;." +
            "&#13;&#10;&#13;&#10;xxx: Любой алкаш сможет, а Оксфордский словарь - не смог )";
    final String CORRECT_QUOTE = """
            "Оксфордский словарь не смог описать 2020 год одним словом".

            xxx: Любой алкаш сможет, а Оксфордский словарь - не смог )""";


    @Test
    public void numberIsCorrectTest() {
        Assert.assertTrue(INPUT_CHECKER.numberIsCorrect(CORRECT_NUMBER));
    }

    @Test
    public void normalizeQuoteTest() {
        Assert.assertEquals(PARSER.normalizeQuote(UNTREATED_QUOTE), CORRECT_QUOTE);
    }

    @Test
    public void getQuoteByNumberTest() {
        Assert.assertEquals(REQUEST_HANDLER.returnQuoteByNumber(CORRECT_NUMBER), CORRECT_QUOTE);
    }

}
