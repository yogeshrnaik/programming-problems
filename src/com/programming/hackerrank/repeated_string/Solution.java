package com.programming.hackerrank.repeated_string;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/challenges/repeated-string/problem
 */
public class Solution {

    // Complete the repeatedString function below.
    static long repeatedString(String s, long n) {
        long result = 0;

        long aCountInString = countAs(s, s.length());
        System.out.println("aCountInString = " + aCountInString);

        if (s.length() > n) {
            return countAs(s, n);
        }

        long multiplesOfString = n / s.length();
        result = multiplesOfString * aCountInString;

        if (n % s.length() != 0) {
            long reminder = n % s.length();
            result = result + countAs(s, reminder);
        }

        return result;
    }

    static long countAs(String s, long n) {
        long count = 0;
        long counter = 0;
        for (char c : s.toCharArray()) {
            if (counter == n) {
                break;
            }
            if (c == 'a') {
                count++;
            }
            counter++;
        }
        return count;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = scanner.nextLine();

        long n = scanner.nextLong();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        long result = repeatedString(s, n);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

