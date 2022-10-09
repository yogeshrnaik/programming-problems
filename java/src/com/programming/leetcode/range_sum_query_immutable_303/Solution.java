package com.programming.leetcode.range_sum_query_immutable_303;

public class Solution {
}

// Problem: //Problem https://leetcode.com/problems/range-sum-query-immutable/
class NumArray {

    private int[] nums;
    private int[] prefixSum;

    public NumArray(int[] nums) {
        this.nums = nums;
        this.prefixSum = new int[nums.length];
        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }
    }

    public int sumRange(int left, int right) {
        int sum = prefixSum[right] - prefixSum[left] + nums[right];
        return sum;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(left,right);
 */