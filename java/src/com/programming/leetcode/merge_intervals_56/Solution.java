package com.programming.leetcode.merge_intervals_56;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Problem: https://leetcode.com/problems/merge-intervals/
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (iv1, iv2) -> Integer.compare(iv1[0], iv2[0]));

        List<int[]> merged = new ArrayList<>();
        merged.add(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            int[] lastInterval = merged.get(merged.size() - 1);
            int currStart = intervals[i][0];
            int currEnd = intervals[i][1];
            int lastEnd = lastInterval[1];
            if (currStart <= lastEnd) {
                // merge
                lastInterval[1] = Math.max(lastEnd, currEnd);
            } else {
                merged.add(intervals[i]);
            }
        }

        return merged.toArray(new int[merged.size()][]);
    }
}