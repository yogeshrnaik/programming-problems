package com.programming.altassian.base7;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Base7Conversion {

    private static final Map<Long, String> base7 = new HashMap<>();

    static {
        base7.put(0L, "0");
        base7.put(1L, "a");
        base7.put(2L, "t");
        base7.put(3L, "l");
        base7.put(4L, "s");
        base7.put(5L, "i");
        base7.put(6L, "n");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            convertFromDecimalToBase7(scanner);
            // convertFromBase7ToDecimal(scanner);
        }
    }

    private static void convertFromDecimalToBase7(Scanner scanner) {
        long number = -1;
        while ((number = scanner.nextLong()) != -1) {
            System.out.println("Number: " + number + " to base7 = " + convertToBase7(number));
        }
    }

    private static String convertToBase7(long number) {
        String result = "";
        while (number != 0) {
            long remainder = number % 7;
            number = number / 7;
            result = base7.get(remainder) + result;
        }
        return result;
    }

    public static void convertFromBase7ToDecimal(Scanner scanner) {
        System.out.println("Enter number in base7 to convert it to Decimal. Enter -1 to exit.");
        String number = "";
        while (!(number = scanner.next()).equals("-1")) {
            System.out.println("Number from base7: " + number + " to decimal = " + convertFromBase7ToDecimal(number));
        }
    }

    private static long convertFromBase7ToDecimal(String number) {
        long sum = 0;
        for (int i = 0; i < number.length(); i++) {
            sum += Integer.parseInt(String.valueOf(number.charAt(i))) * Math.pow(7, number.length() - i - 1);
        }
        return sum;
    }
}
