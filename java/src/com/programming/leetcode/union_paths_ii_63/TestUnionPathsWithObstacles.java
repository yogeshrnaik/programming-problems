package com.programming.leetcode.union_paths_ii_63;

import org.junit.Assert;
import org.junit.Test;

public class TestUnionPathsWithObstacles {

    @Test
    public void testUnionPathsWithObstacles_sample1() {
        int[][] grid = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        Assert.assertEquals(2, new Solution().uniquePathsWithObstacles(grid));
    }

    @Test
    public void testUnionPathsWithObstacles_sample2() {
        int[][] grid = {
                {0, 1},
                {0, 0}
        };
        Assert.assertEquals(1, new Solution().uniquePathsWithObstacles(grid));
    }
}
