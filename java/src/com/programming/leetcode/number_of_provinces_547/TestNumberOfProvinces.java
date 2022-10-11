package com.programming.leetcode.number_of_provinces_547;

import org.junit.Assert;
import org.junit.Test;

public class TestNumberOfProvinces {

    @Test
    public void testNumberOfProvinces_sample1() {
        int actual = new Solution().findCircleNum(new int[][]{
                {1, 1, 0},
                {1, 1, 0},
                {0, 0, 1},
        });
        Assert.assertEquals(2, actual);
    }

    @Test
    public void testNumberOfProvinces_sample2() {
        int actual = new Solution().findCircleNum(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1},
        });
        Assert.assertEquals(3, actual);
    }
}
