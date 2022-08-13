package com.programming.leetcode.union_paths_ii_63;

import java.util.Arrays;

/**
 * Problem: https://leetcode.com/problems/unique-paths-ii/
 */

class Solution {

    public int uniquePathsWithObstacles(int[][] grid) {
        int r = grid.length;
        int c = grid[0].length;
        int[][] dp = new int[r][c];
        for (int[] a : dp) {
            Arrays.fill(a, -1);
        }

        return gridTraveller(grid, 0, 0, dp);
    }

    public int gridTraveller(int[][] grid, int r, int c, int[][] dp) {
        if (r < 0 || c < 0 || r > grid.length - 1 || c > grid[0].length - 1 || grid[r][c] == 1) {
            return 0;
        }

        if (r == grid.length - 1 && c == grid[0].length - 1) {
            return 1;
        }
        if (dp[r][c] > -1) return dp[r][c];
        dp[r][c] = gridTraveller(grid, r + 1, c, dp) + gridTraveller(grid, r, c + 1, dp);
        return dp[r][c];

    }
}