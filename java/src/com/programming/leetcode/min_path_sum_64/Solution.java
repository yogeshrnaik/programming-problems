package com.programming.leetcode.min_path_sum_64;

/**
 * Problem: https://leetcode.com/problems/minimum-path-sum/
 */
class Solution {

    public int minPathSum(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == 0 && j != 0) {
                    // First row
                    grid[i][j] += grid[i][j - 1];
                }
                if (i != 0 && j == 0) {
                    // First column
                    grid[i][j] += grid[i - 1][j];
                }
                if (i != 0 && j != 0) {
                    // All other cells
                    grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
                }
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];
    }

}