package com.programming.leetcode.jump_game_55;


// Problem: https://leetcode.com/problems/jump-game/
class Solution {
    public boolean canJump(int[] nums) {
        int lastReachableIndex = nums.length - 1;

        for (int i = nums.length - 1; i >= 0; i--) {
            if (i + nums[i] >= lastReachableIndex) {
                lastReachableIndex = i;
            }
        }

        return lastReachableIndex == 0;
    }
}
