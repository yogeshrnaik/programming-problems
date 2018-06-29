package com.programming.altassian;

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
        base7.put(6L, "N");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            long number = -1;
            while ((number = scanner.nextLong()) != -1) {
                System.out.println("Number: " + number + " to base7 = " + convertToBase7(number));
            }
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
}
