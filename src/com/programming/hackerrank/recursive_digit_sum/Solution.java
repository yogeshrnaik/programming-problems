package com.programming.hackerrank.recursive_digit_sum;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/recursive-digit-sum/problem
 */
public class Solution {

    // Complete the superDigit function below.
    static int superDigit(String n, int k) {
        long sumOfDigits = sumOfDigits(n) * k;

        while (String.valueOf(sumOfDigits).length() > 1) {
            sumOfDigits = sumOfDigits(String.valueOf(sumOfDigits));
        }
        return Long.valueOf(sumOfDigits).intValue();
    }

    static long sumOfDigits(String n) {
        long sum = 0;
        for (char c : n.toCharArray()) {
            sum += Integer.valueOf(String.valueOf(c));
        }
        return sum;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nk = scanner.nextLine().split(" ");

        String n = nk[0];

        int k = Integer.parseInt(nk[1]);

        int result = superDigit(n, k);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
