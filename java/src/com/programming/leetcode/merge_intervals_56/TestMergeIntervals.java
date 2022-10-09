package com.programming.leetcode.merge_intervals_56;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class TestMergeIntervals {

    @Test
    public void testMergeIntervals_sample1() {
        int[][] merged = new Solution().merge(new int[][]{
                {1, 3},
                {2, 6},
                {8, 10},
                {15, 18},
        });

        int[][] expected = new int[][]{
                {1, 6},
                {8, 10},
                {15, 18},
        };

        assertTrue(Arrays.deepEquals(expected, merged));
    }

    @Test
    public void testMergeIntervals_sample2() {
        int[][] merged = new Solution().merge(new int[][]{
                {1, 4},
                {4, 5},
        });

        int[][] expected = new int[][]{
                {1, 5},
        };

        assertTrue(Arrays.deepEquals(expected, merged));
    }

    @Test
    public void testMergeIntervals_sample3() {
        int[][] merged = new Solution().merge(new int[][]{
                {1, 10},
                {4, 5},
        });

        int[][] expected = new int[][]{
                {1, 10},
        };

        assertTrue(Arrays.deepEquals(expected, merged));
    }

    @Test
    public void testMergeIntervals_sample4() {
        int[][] merged = new Solution().merge(new int[][]{
                {1, 10},
                {11, 15},
        });

        int[][] expected = new int[][]{
                {1, 10},
                {11, 15},
        };

        assertTrue(Arrays.deepEquals(expected, merged));
    }
}
