package com.programming.leetcode.snakes_and_ladders_909;

import org.junit.Assert;
import org.junit.Test;

public class TestSnakeAndLadder {

    @Test
    public void testSnakeAndLadder_sample1() {
        int[][] board = new int[][]{
                {1, 1, -1},
                {1, 1, 1},
                {-1, 1, 1},
        };
        int moves = new Solution().snakesAndLadders(board);
        Assert.assertEquals(-1, moves);
    }

    @Test
    public void testSnakeAndLadder_sample2() {
        int[][] board = new int[][]{
                {-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1},
                {-1,35,-1,-1,13,-1},
                {-1,-1,-1,-1,-1,-1},
                {-1,15,-1,-1,-1,-1},
        };
        int moves = new Solution().snakesAndLadders(board);
        Assert.assertEquals(4, moves);
    }
}
