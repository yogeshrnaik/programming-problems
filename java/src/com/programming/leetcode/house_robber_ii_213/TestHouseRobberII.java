package com.programming.leetcode.house_robber_ii_213;

import org.junit.Assert;
import org.junit.Test;

public class TestHouseRobberII {

    @Test
    public void test_house_robber_ii_sample1() {
        Assert.assertEquals(3, new Solution().rob(new int[]{2, 3, 2}));
    }

    @Test
    public void test_house_robber_ii_sample2() {
        Assert.assertEquals(4, new Solution().rob(new int[]{1, 2, 3, 1}));
    }

    @Test
    public void test_house_robber_ii_sample3() {
        Assert.assertEquals(340, new Solution().rob(new int[]{200, 3, 140, 20, 10}));
    }

    @Test
    public void test_house_robber_ii_sample4() {
        Assert.assertEquals(1, new Solution().rob(new int[]{1}));
    }
}
