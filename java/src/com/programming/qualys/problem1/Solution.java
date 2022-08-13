package com.programming.qualys.problem1;

/**
 * https://www.hackerrank.com/tests/8nmld58408c/questions/5ekgg99be9t
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

class Result {

    /*
     * Complete the 'breakPalindrome' function below. The function is expected to return a STRING. The function accepts STRING s as
     * parameter.
     */

    public static String breakPalindrome(String s) {
        // Write your code here
        String output = "IMPOSSIBLE";

        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != 'a') {
                return s.substring(0, i) + "a" + s.substring(i + 1);
            }
        }

        return output;
    }

}

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        String result = Result.breakPalindrome(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
