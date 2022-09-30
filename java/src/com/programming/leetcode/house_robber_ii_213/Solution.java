package com.programming.leetcode.house_robber_ii_213;

import java.util.Arrays;
import java.util.List;

/**
 * Problem: https://leetcode.com/problems/house-robber-ii/
 */
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        return Math.max(
                rob1(Arrays.copyOfRange(nums, 0, nums.length - 1)),
                rob1(Arrays.copyOfRange(nums, 1, nums.length))
        );
    }

    public int rob1(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
        }

        return dp[nums.length - 1];
    }
}