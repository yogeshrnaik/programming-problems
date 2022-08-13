package com.programming.spotify;

import java.util.ArrayList;
import java.util.List;


/**
 * [[a, b], [d, e]] <br/>
 * [[a, b, c, x, y, z], [d, e]] <br/>
 * [[a, b], [d, e, f, g, h]] <br/>
 * <p>
 * Your previous Plain Text content is preserved below: <br/>
 * Generate all combinations from a 2D list. <br/>
 * E.g. if the input is [[a, b], [c], [d, e]], the output should be acd, ace, bcd, bce <br/>
 * edge cases <br/>
 * 1) what is list is empty? - empty string <br/>
 * 2) one list with only one item in it - output that item - e.g. [a] = [a] <br/>
 * 3) one list with multiple items in it - then output each item separately - [a,b,c] = [a, b, c] <br/>
 * <p>
 */
class Solution {

    public List<String> generateCombinations(List<List<String>> input) {
        List<String> result = new ArrayList<>();
        if (input == null || input.size() == 0) {
            result.add("");
            return result;
        }
        combination(input, result, 0, "");
        return result;
    }

    static void combination(List<List<String>> input, List<String> result, int start, String startChar) {
        if (start == input.size() - 1) {
            for (String s : input.get(start)) {
                result.add(startChar + s);
            }
        } else {
            for (String v : input.get(start)) {
                combination(input, result, start + 1, startChar + v);
            }
        }
    }

    // Code to demonstrate how to arrive at recursion logic
    static void iteration(List<List<String>> input, List<String> result) {
        if (input.size() == 2) {
            for (String i : input.get(0)) {
                for (String j : input.get(1)) {
                    result.add(i + j);
                }
            }
        }

        if (input.size() == 3) {
            for (String i : input.get(0)) {
                for (String j : input.get(1)) {
                    for (String k : input.get(2)) {
                        result.add(i + j + k);
                    }
                }
            }
        }
    }
}