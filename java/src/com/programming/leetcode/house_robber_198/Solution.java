package com.programming.leetcode.house_robber_198;

import java.util.Arrays;

/**
 * Problem: https://leetcode.com/problems/house-robber/
 */
class Solution {
    public int rob(int[] nums) {
        return constant_memory(nums);
    }

    public int constant_memory(int[] nums) {
        int rob1 = 0, rob2 = 0;
        // rob1, rob2, n, n+1
        for (int n : nums) {
            int temp = Math.max(rob1 + n, rob2);
            rob1 = rob2;
            rob2 = temp;
        }
        return rob2;
    }

        private static int n_memory(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
        }

        return dp[nums.length - 1];
    }
}