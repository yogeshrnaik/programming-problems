package com.programming.spotify;

import java.util.ArrayList;
import java.util.List;

/*
 * To execute Java, please define "static void main" on a class named Solution. If you need more classes, simply define them inline.
 */

class Solution {

    public List<String> generateCombinations(List<List<String>> input) {
        List<String> result = new ArrayList<>();

        if (input == null || input.size() == 0) {
            result.add("");
        }
        if (input != null && input.size() == 1) {
            result.addAll(input.get(0));
        }

        if (input.size() == 2) {
            for (String i : input.get(0)) {
                for (String j : input.get(1)) {
                    result.add(i + j);
                }
            }
        }

        return result;
    }
}
/**
 * [[a, b], [d, e]] <br/>
 * [[a, b, c, x, y, z], [d, e]] <br/>
 * [[a, b], [d, e, f, g, h]] <br/>
 */
/**
 * Your previous Plain Text content is preserved below: <br/>
 * Generate all combinations from a 2D list. <br/>
 * E.g. if the input is [[a, b], [c], [d, e]], the output should be acd, ace, bcd, bce <br/>
 * edge cases <br/>
 * 1) what is list is empty? - empty string <br/>
 * 2) one list with only one item in it - output that item - e.g. [a] = [a] <br/>
 * 3) one list with multiple items in it - then output each item separately - [a,b,c] = [a, b, c] <br/>
 */