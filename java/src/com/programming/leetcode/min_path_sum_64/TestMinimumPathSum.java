package com.programming.leetcode.min_path_sum_64;

import org.junit.Assert;
import org.junit.Test;

public class TestMinimumPathSum {

    @Test
    public void testMinPathSum_sample1() {
        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        Assert.assertEquals(7, new Solution().minPathSum(grid));
    }

    @Test
    public void testMinPathSum_sample2() {
        int[][] grid = {
                {1, 2, 3},
                {4, 5, 6}
        };
        Assert.assertEquals(12, new Solution().minPathSum(grid));
    }
}
