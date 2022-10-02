package com.programming.hackerrank.beautiful_days;

// Problem: https://www.hackerrank.com/challenges/beautiful-days-at-the-movies/problem

public class Result {
    /*
     * Complete the 'beautifulDays' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER i
     *  2. INTEGER j
     *  3. INTEGER k
     */

    public static int beautifulDays(int i, int j, int k) {
        int count = 0;
        for (int c = i; c <= j; c++) {
            int abs = Math.abs(c - reverse(c));
            if (abs % k == 0) count++;
        }
        return count;
    }

    public static int reverse(int num) {
        int reversed = 0;
        while (num != 0) {
            int digit = num % 10;
            reversed = reversed * 10 + digit;
            num /= 10;
        }
        return reversed;
    }
}
