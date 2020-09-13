package com.programming.booking.exam2.problem2;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;



class Result {

    /*
     * Complete the 'largestArea' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY samples as parameter.
     */

    public static int largestArea(List<List<Integer>> samples) {
        // Write your code here

        for (int i : samples.get(0)) {
            for (int j : samples.get(i)) {
                if (i == j && i == 1) {

                }
            }
        }
        return 0;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int samplesRows = Integer.parseInt(bufferedReader.readLine().trim());
        int samplesColumns = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> samples = new ArrayList<>();

        IntStream.range(0, samplesRows).forEach(i -> {
            try {
                samples.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.largestArea(samples);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
