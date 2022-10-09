package com.programming.leetcode.jump_game_55;

import org.junit.Assert;
import org.junit.Test;

public class TestJumpGame {

    @Test
    public void testJumpGame() {
        Assert.assertTrue(new Solution().canJump(new int[]{2,3,1,1,4}));
    }
}
