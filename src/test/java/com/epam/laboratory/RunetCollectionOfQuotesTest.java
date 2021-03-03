package com.epam.laboratory;

import static org.hamcrest.core.Is.is;

import com.epam.laboratory.exceptions.NotANumberException;
import com.epam.laboratory.exceptions.NumberOutOfRangeException;
import org.junit.Assert;
import org.junit.Test;

public class RunetCollectionOfQuotesTest {

    final InputChecker INPUT_CHECKER = new InputChecker();
    final Parser PARSER = new Parser();
    final WebPageLoader WEB_PAGE_LOADER = new WebPageLoader();

    final String INCORRECT_NUMBER_BY_LENGTH = "1234567";
    final String INCORRECT_NUMBER_BY_SYMBOLS = "123p56";
    final String CORRECT_NUMBER = "463765";
    final String CORRECT_QUOTE_URL = "https://bash.im/quote/463765";

    final String UNTREATED_QUOTE = "&quot;Оксфордский словарь не смог описать 2020 год одним словом&quot;.&#13;&#10;&#13;&#10;xxx: Любой алкаш сможет, а Оксфордский словарь - не смог )";
    final String QUOTE_WITH_INCORRECT_QUOTES = """
            &quot;Оксфордский словарь не смог описать 2020 год одним словом&quot;.

            xxx: Любой алкаш сможет, а Оксфордский словарь - не смог )""";
    final String QUOTE_WITH_INCORRECT_LINE_BREAK = "\"Оксфордский словарь не смог описать 2020 год одним словом\".&#13;&#10;&#13;&#10;xxx: Любой алкаш сможет, а Оксфордский словарь - не смог )";

    final String CORRECT_QUOTE = """
            "Оксфордский словарь не смог описать 2020 год одним словом".

            xxx: Любой алкаш сможет, а Оксфордский словарь - не смог )""";

    final String UNTREATED_SINGLE_TREE_QUOTES = "&lt;Дор&gt; &quot;мышка, почему у тебя такие большие глаза?&quot; УЙДИ!!! я ХАРАКИРИ делаю!!!!!!";
    final String CORRECT_LEFT_SINGLE_TREE_QUOTES = "<Дор&gt; &quot;мышка, почему у тебя такие большие глаза?&quot; УЙДИ!!! я ХАРАКИРИ делаю!!!!!!";
    final String CORRECT_RIGHT_SINGLE_TREE_QUOTES = "&lt;Дор> &quot;мышка, почему у тебя такие большие глаза?&quot; УЙДИ!!! я ХАРАКИРИ делаю!!!!!!";


    final String NUMBER_OUT_OF_RANGE_EXCEPTION_MESSAGE = "Input number not included in the range from 000001 to 464739 inclusive";
    final String NOT_A_NUMBER_EXCEPTION_MESSAGE = "Input string not a number";




    // проверяем что строка, поданная на вход, содержит только цифры (является числом)
    @Test
    public void numberContainsJustNumericsTest(){
        try {
            INPUT_CHECKER.numberContainsJustNumerics(INCORRECT_NUMBER_BY_SYMBOLS);
        } catch (NotANumberException exception){
            Assert.assertThat(exception.getMessage(), is(NOT_A_NUMBER_EXCEPTION_MESSAGE));
        }
    }

    // проверяем что строка, поданная на вход, входит в диапазон
    @Test
    public void numberIncludedInRangeTest(){
        try {
            INPUT_CHECKER.numberIncludedInRange(INCORRECT_NUMBER_BY_LENGTH);
        } catch (NumberOutOfRangeException exception){
            Assert.assertThat(exception.getMessage(), is(NUMBER_OUT_OF_RANGE_EXCEPTION_MESSAGE));
        }
    }

    @Test
    public void numberIsCorrectTest() throws NumberOutOfRangeException, NotANumberException{
        Assert.assertTrue(INPUT_CHECKER.numberIsCorrect(CORRECT_NUMBER));
    }


    // "пристыкуем" number к url и проверяем как прошло
    @Test
    public void configureUrlForRequestTest(){
        Assert.assertEquals(PARSER.configureUrlForRequest(CORRECT_NUMBER), CORRECT_QUOTE_URL);
    }

    // проверяем как считалась необработанная цитата
    @Test
    public void loadQuoteByNumberTest(){
        Assert.assertEquals(WebPageLoader.loadQuoteByNumber(CORRECT_NUMBER), UNTREATED_QUOTE);
    }

    // заменяем все &gt; на >
    @Test
    public void replaceRightSingleTreeQuotesTest(){
        Assert.assertEquals(Parser.replaceRightSingleTreeQuotes(UNTREATED_SINGLE_TREE_QUOTES), CORRECT_RIGHT_SINGLE_TREE_QUOTES);
    }
    // заменяем все &lt; на <
    @Test
    public void replaceLeftSingleTreeQuotesTest(){
        Assert.assertEquals(Parser.replaceLeftSingleTreeQuotes(UNTREATED_SINGLE_TREE_QUOTES), CORRECT_LEFT_SINGLE_TREE_QUOTES);
    }

    // заменяем все &quot; на "
    @Test
    public void replaceClassicQuotesTest(){
        Assert.assertEquals(Parser.replaceClassicQuotes(UNTREATED_QUOTE), QUOTE_WITH_INCORRECT_LINE_BREAK);
    }

    // заменяем все &#13;&#10; на /n
    @Test
    public void replaceAllNewLinesTest(){
        Assert.assertEquals(Parser.replaceAllNewLines(UNTREATED_QUOTE), QUOTE_WITH_INCORRECT_QUOTES);
    }

    // проверка "причёсывания" исходной цитаты
    @Test
    public void normalizeQuoteTest(){
        Assert.assertEquals(PARSER.normalizeQuote(UNTREATED_QUOTE), CORRECT_QUOTE);
    }

    @Test
    public void getQuoteByNumberTest(){
        Assert.assertEquals(WEB_PAGE_LOADER.getQuoteByNumber(CORRECT_NUMBER), CORRECT_QUOTE);
    }

}
