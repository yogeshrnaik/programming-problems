package com.programming.qualys.sampletest.problem1;

import java.util.List;

public class Solution {

    static String findNumber(List<Integer> arr, int k) {
        return (arr.stream()
            .filter(i -> i == k)
            .findFirst()
            .isPresent()) ? "YES" : "NO";
    }
}
