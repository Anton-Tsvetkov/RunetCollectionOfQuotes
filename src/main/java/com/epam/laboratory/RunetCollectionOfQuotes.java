package com.epam.laboratory;

import java.util.Scanner;

public class RunetCollectionOfQuotes {
    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Print number of quote: ");
        String number = scanner.nextLine();
        System.out.println(new RequestHandler().returnQuoteByNumber(number));
    }
}
