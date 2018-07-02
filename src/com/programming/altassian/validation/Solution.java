package com.programming.altassian.validation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Solution {

    /*
     * Complete the validate function below.
     */
    static String validate(String input) {
        /*
         * Write your code here.
         */

        StringTokenizer lineTokenizer = new StringTokenizer(input, "|");
        String token = "";
        List<String> names = new ArrayList<>();
        while (!(token = lineTokenizer.nextToken()).equals("~n")) {
            names.add(token);
        }
        if (names.size() == 0) {
            return "0:0:0:format_error";
        }

        int result[] = new int[names.size()];

        // read data
        int i = 0;
        while (lineTokenizer.hasMoreTokens()) {
            token = lineTokenizer.nextToken();
            if (token.equals("~n")) {
                i = 0;
            } else {
                if (token.length() > 0)
                    result[i] += 1;
                if (i < names.size() - 1)
                    i++;
            }
        }

        String ans = "";
        return ans;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println(validate("|name|address|~n|Patrick|patrick@test.com|pat@test.com|~n|Annie||annie@test.com|~n"));
        // mainSample();
    }

    private static void mainSample() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String input = scanner.nextLine();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        String res = validate(input);

        bufferedWriter.write(res);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
