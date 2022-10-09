package com.programming.leetcode.jump_game_ii_45.jump_game_55;
import java.lang.Math;


// Problem: https://leetcode.com/problems/jump-game-ii/
class Solution {
    public int jump(int[] nums) {
        int count = 0;
        int left = 0;
        int right = 0;
        while(right < nums.length - 1) {
            int farthestJumpIndex = 0;
            for (int i=left; i<=right; i++) {
                farthestJumpIndex = Math.max(farthestJumpIndex, nums[i] + i);
            }

            left = right + 1;
            right = farthestJumpIndex;
            count++;
        }

        return count;
    }
}