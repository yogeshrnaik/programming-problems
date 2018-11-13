package com.programming.hackerrank.diagonal_diff;

import java.io.IOException;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/diagonal-difference/problem
 */
public class Solution {

    // Complete the diagonalDifference function below.
    static int diagonalDifference(int[][] arr) {
        int primary = 0;
        int secondary = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (i == j) {
                    primary += arr[i][j];
                }
                if (i + j == arr.length - 1) {
                    secondary += arr[i][j];
                }
            }
        }

        return Math.abs(primary - secondary);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        // BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[][] arr = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] arrRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < n; j++) {
                int arrItem = Integer.parseInt(arrRowItems[j]);
                arr[i][j] = arrItem;
            }
        }

        int result = diagonalDifference(arr);
        System.out.println(result);

        // bufferedWriter.write(String.valueOf(result));
        // bufferedWriter.newLine();
        //
        // bufferedWriter.close();

        scanner.close();
    }
}
