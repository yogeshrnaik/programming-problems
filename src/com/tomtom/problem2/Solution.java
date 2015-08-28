package com.tomtom.problem2;

import java.util.ArrayList;
import java.util.List;

class Solution {

    public static final int NO_ADJACENT_INDICES = -2;
    public static final int DIFF_GT_MAX_DIFF_THRESHOLD = -1;
    public static final int MAX_DIFF_THRESHOLD = 1000000000;

    public int solution(int[] A) {
        if (A == null || A.length == 0 || A.length == 1) {
            return NO_ADJACENT_INDICES;
        }

        if (A.length == 2) {
            int result = Math.abs(A[0] - A[1]);
            return result > MAX_DIFF_THRESHOLD ? DIFF_GT_MAX_DIFF_THRESHOLD : result;
        }

        List<Integer> input = toList(A);
        int maxDiff = Integer.MIN_VALUE;
        boolean hasAtleastOneAdjacentIndicesPair = false;

        for (int p = 0; p < A.length - 1; p++) {
            for (int q = p + 1; q < A.length; q++) {
                if (areAdjacentIndices(input, A[p], A[q])) {
//                    System.out.println("(" + p + ", " + q + ")");
                    hasAtleastOneAdjacentIndicesPair = true;
                    int diff = Math.abs(A[p] - A[q]);
                    if (diff > maxDiff) {
                        maxDiff = diff;
                    }
                }
            }
        }

        int result = hasAtleastOneAdjacentIndicesPair ? maxDiff : NO_ADJACENT_INDICES; // default
        if (hasAtleastOneAdjacentIndicesPair && maxDiff > MAX_DIFF_THRESHOLD) {
            result = DIFF_GT_MAX_DIFF_THRESHOLD;
        }
        return result;
    }

    private boolean areAdjacentIndices(List<Integer> input, int p, int q) {
        int min = p < q ? p : q;
        int max = p > q ? p : q;

        for (int i=min+1; i < max; i++) {
            if (input.contains(i)) {
                return false;
            }
        }
        return true;
    }


    private List<Integer> toList(int[] a) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i : a) {
            result.add(i);
        }
        return result;
    }
}