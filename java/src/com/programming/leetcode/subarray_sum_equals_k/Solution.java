package com.programming.leetcode.subarray_sum_equals_k;

import java.util.HashMap;
import java.util.Map;

// Problem: https://leetcode.com/problems/subarray-sum-equals-k/
class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> sums = new HashMap<>();
        sums.put(0, 1);
        int sum = 0;
        int count = 0;
        for (int n : nums) {
            sum += n;

            if (sums.containsKey(sum - k)) {
                count += sums.get(sum - k);
            }
            sums.put(sum, 1 + sums.getOrDefault(sum, 0));
        }

        return count;
    }
}