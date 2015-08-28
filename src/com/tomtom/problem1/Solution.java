package com.tomtom.problem1;

// you can also use imports, for example:
// import java.util.*;

// you can use System.out.println for debugging purposes, e.g.
// System.out.println("this is a debug message");
class Solution {
    public int solution(int[] A) {
        // write your code in Java SE 8
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int a : A) {
            max = (a > max) ? a : max;
            min = (a < min) ? a : min;
        }
        return max - min;
    }
}