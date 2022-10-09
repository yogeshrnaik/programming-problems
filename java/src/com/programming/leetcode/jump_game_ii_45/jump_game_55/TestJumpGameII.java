package com.programming.leetcode.jump_game_ii_45.jump_game_55;

import org.junit.Assert;
import org.junit.Test;

public class TestJumpGameII {

    @Test
    public void testJumpGameII() {
        Assert.assertEquals(2, new Solution().jump(new int[]{2, 3, 1, 1, 4}));
        Assert.assertEquals(2, new Solution().jump(new int[]{2, 3, 0, 1, 4}));
    }
}
