package com.programming.altassian.looknsay;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Solution {
    /*
     * Complete the function below.
     */

    static String LookAndSay(String start, int n) {
        String result = start;
        for (int i = 1; i <= n; i++) {
            result = LookAndSay(result);
        }
        return result;
    }

    public static String LookAndSay(final String num) {
        int currCharIndex = 0;
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < num.length(); i++) {
            char currChar = num.charAt(currCharIndex);
            if (currChar != num.charAt(i)) {
                // digits between charIndex till index i-1 are same. separate them out
                final String digitsFound = num.substring(currCharIndex, i);

                // append to result the number of times same digit occurred and the digit's value
                result.append(digitsFound.length()).append(currChar);
                currCharIndex = i;
            }
        }

        // count the remaining digits
        result.append(num.substring(currCharIndex, num.length()).length())
            .append(num.charAt(currCharIndex));
        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        final String fileName = System.getenv("OUTPUT_PATH");
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        String res;
        String _start;
        try {
            _start = in.nextLine();
        } catch (Exception e) {
            _start = null;
        }

        int _n;
        _n = Integer.parseInt(in.nextLine());

        res = LookAndSay(_start, _n);
        bw.write(res);
        bw.newLine();

        bw.close();
    }
}