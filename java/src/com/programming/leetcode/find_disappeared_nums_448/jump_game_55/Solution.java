package com.programming.leetcode.find_disappeared_nums_448.jump_game_55;


import java.util.ArrayList;
import java.util.List;

// Problem: https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int value = Math.abs(nums[i]);
            nums[value-1] = -1 * Math.abs(nums[value-1]);
        }

        List<Integer> disappearedNums = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                disappearedNums.add(i+1);
            }
        }

        return disappearedNums;
    }
}