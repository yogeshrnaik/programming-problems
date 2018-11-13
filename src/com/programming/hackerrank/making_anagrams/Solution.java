package com.programming.hackerrank.making_anagrams;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/making-anagrams/problem
 */
public class Solution {

    // Complete the makingAnagrams function below.
    static int makingAnagrams(String s1, String s2) {
        int noOfCharsToBeDeleted = 0;

        int[] a1 = countCharacters(s1);
        int[] a2 = countCharacters(s2);

        for (int i = 0; i < a1.length; i++) {
            noOfCharsToBeDeleted += Math.abs(a1[i] - a2[i]);
        }

        return noOfCharsToBeDeleted;
    }

    static int[] countCharacters(String s) {
        int[] count = new int[26];
        final int ASCII_LOWERCASE_A = 97;

        for (char c : s.toCharArray()) {
            count[(int)c - ASCII_LOWERCASE_A]++;
        }
        return count;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s1 = scanner.nextLine();

        String s2 = scanner.nextLine();

        int result = makingAnagrams(s1, s2);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
        // System.out.println(makingAnagrams("abc", "cde"));
    }

    private static void testUsingSystemEnv() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s1 = scanner.nextLine();

        String s2 = scanner.nextLine();

        int result = makingAnagrams(s1, s2);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
