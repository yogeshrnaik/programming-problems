package com.programming.leetcode.spiral_matrix_54;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestSpiralMatrix {

    @Test
    public void testSpiralMatrix() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };
        List<Integer> actual = new Solution().spiralOrder(matrix);
        List<Integer> expected = List.of(1, 2, 3, 6, 9, 8, 7, 4, 5);
        Assert.assertEquals(expected, actual);
    }
}
