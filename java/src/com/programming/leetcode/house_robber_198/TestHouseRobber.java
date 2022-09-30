package com.programming.leetcode.house_robber_198;

import org.junit.Assert;
import org.junit.Test;

public class TestHouseRobber {

    @Test
    public void test_house_robber_sample1() {
        Assert.assertEquals(4, new Solution().rob(new int[]{1, 2, 3, 1}));
    }

    @Test
    public void test_house_robber_sample2() {
        Assert.assertEquals(12, new Solution().rob(new int[]{2, 7, 9, 3, 1}));
    }
}
