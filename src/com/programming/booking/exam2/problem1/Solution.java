package com.programming.booking.exam2.problem1;

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
     * Complete the 'funWithAnagrams' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts STRING_ARRAY text as parameter.
     */

    public static List<String> funWithAnagrams(List<String> text) {
        // Write your code here
        List<String> result = new ArrayList<>();
        Set<String> anagramsFound = new HashSet<>();
        for (String word : text) {
            String sortedWord = sortChars(word);
            if (!anagramsFound.contains(sortedWord)) {
                result.add(word);
                anagramsFound.add(sortedWord);
            }
        }
        Collections.sort(result);
        return result;
    }

    public static String sortChars(String word) {
        char[] wordChars = word.toCharArray();
        Arrays.sort(wordChars);
        return new String(wordChars);
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int textCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> text = IntStream.range(0, textCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
                .collect(toList());

        List<String> result = Result.funWithAnagrams(text);

        bufferedWriter.write(
                result.stream()
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
