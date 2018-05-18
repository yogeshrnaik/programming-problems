package com.programming.machinesequence;

import java.util.StringTokenizer;

public class CandidateCode {

    public static String sequences(String input1) {
        if (invalidInput(input1)) {
            return input1;
        }

        long[] inputSeq = parseInput(input1);
        if (inputSeq == null || inputSeq.length == 0) {
            return input1;
        }

        String result = "";

        if (inputSeq.length == 1) {
            result = String.valueOf(inputSeq[0]);
        } else {
            long[] stepDiff = inputSeq;
            do {
                // System.out.println(Arrays.toString(stepDiff));
                stepDiff = calculateStepDifference(stepDiff);

            } while (stepDiff.length != 1);

            result = String.valueOf(stepDiff[0]);
        }

        // remove last decimal and zero after that if exists
        // result = result.endsWith(".0") ? result.substring(0, result.length() - 2) : result;
        return result;
    }

    private static long[] parseInput(String input) {
        long[] result = null;

        try {
            // split the input sequence
            if (input != null && input.length() > 0) {
                StringTokenizer tokenizer = new StringTokenizer(input, ",");
                result = new long[tokenizer.countTokens()];
                int index = 0;
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken().trim();
                    result[index++] = Long.parseLong(token);
                }
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return result;
    }

    private static long[] calculateStepDifference(long[] inputSeq) {
        long[] stepDiff = new long[inputSeq.length - 1];

        for (int index = 1; index < inputSeq.length; index++) {
            stepDiff[index - 1] = inputSeq[index] - inputSeq[index - 1];
        }
        return stepDiff;
    }

    private static boolean invalidInput(String input) {
        if (input == null || input.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
