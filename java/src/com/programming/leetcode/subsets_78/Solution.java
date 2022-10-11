package com.programming.leetcode.subsets_78;

import java.util.ArrayList;
import java.util.List;

// Problem: https://leetcode.com/problems/subsets/
public class Solution {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currSubset = new ArrayList<>();
        generate(nums, 0, currSubset, result);
        return result;
    }

    private void generate(int[] nums, int curr, List<Integer> subset, List<List<Integer>> result) {
        if (curr == nums.length) {
            result.add(new ArrayList<>(subset));
            return;
        }

        // include nums[curr]
        subset.add(nums[curr]);
        generate(nums, curr + 1, subset, result);
        subset.remove(subset.size() - 1);

        // do not include nums[curr]
        generate(nums, curr + 1, subset, result);
    }
}