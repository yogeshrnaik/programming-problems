package com.programming.leetcode.k_closest_points_to_origin_973;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static java.util.Arrays.deepEquals;
import static org.junit.Assert.assertTrue;

public class TestKClosedPoints {

    @Test
    public void testKClosedPoints_sample1() {
        int[][] points = new int[][]{
                {1, 3},
                {-2, 2}
        };

        int[][] expected = new int[][]{
                {-2, 2},
        };

        assertTrue(deepEquals(expected, new Solution().kClosest(points, 1)));
    }

    @Test
    public void testKClosedPoints_sample2() {
        int[][] points = new int[][]{
                {3, 3},
                {5, -1},
                {-2, 4}
        };

        int[][] expected = new int[][]{
                {-2, 4},
                {3, 3}
        };

        assertTrue(deepEquals(expected, new Solution().kClosest(points, 2)));
    }
}
