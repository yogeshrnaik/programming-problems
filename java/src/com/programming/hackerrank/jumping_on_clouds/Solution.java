package com.programming.hackerrank.jumping_on_clouds;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/challenges/jumping-on-the-clouds/problem
 */
public class Solution {

    // Complete the jumpingOnClouds function below.
    static int jumpingOnClouds(int[] c) {
        int currIdx = 0;
        int jumps = 0;
        // 0 0 1 0 0 1 0
        // if next to next == 1 then jump to next one
        // if next to next == 0 then jump to next to next
        while (currIdx < c.length) {
            if (currIdx+1 == c.length) {
                // we are at last index
                return jumps;
            } else if (currIdx+2 == c.length) {
                // we are at 2nd last index
                return ++jumps;
            } else if (c[currIdx+2] == 1) {
                currIdx = currIdx + 1;
                jumps++;
            } else if (c[currIdx+2] == 0) {
                currIdx = currIdx + 2;
                jumps++;
            }
        }
        return jumps;

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] c = new int[n];

        String[] cItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int cItem = Integer.parseInt(cItems[i]);
            c[i] = cItem;
        }

        int result = jumpingOnClouds(c);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
