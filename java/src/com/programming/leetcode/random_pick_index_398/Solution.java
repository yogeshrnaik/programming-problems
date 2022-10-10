package com.programming.leetcode.random_pick_index_398;

import java.util.*;

class Solution {

    private int[] nums;
    Random random = new Random();
    private Map<Integer, List<Integer>> counts = new HashMap<>();

    public Solution(int[] nums) {
        this.nums = nums;
        for (int i = 0; i < nums.length; i++) {
            if (counts.get(nums[i]) == null) {
                counts.put(nums[i], new ArrayList<>());
            }
            counts.get(nums[i]).add(i);
        }
    }

    public int pick(int target) {
        int count = 0;
        List<Integer> targetIndices = counts.get(target);
        int rand = random.nextInt(targetIndices.size());
        return targetIndices.get(rand);
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int param_1 = obj.pick(target);
 */