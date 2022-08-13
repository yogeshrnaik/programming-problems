package com.programming.leetcode.combination_sum_39;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestCombinationSum {


    @Test
    public void testCombinationSum_sample1() {
        List<List<Integer>> result = Arrays.asList(Arrays.asList(2, 2, 3), Arrays.asList(7));
        Assert.assertEquals(result, new Solution().combinationSum(new int[]{2, 3, 6, 7}, 7));
    }

    @Test
    public void testCombinationSum_sample2() {
        List<List<Integer>> result = Arrays.asList(Arrays.asList(2, 2, 2, 2), Arrays.asList(2, 3, 3), Arrays.asList(3, 5));
        Assert.assertEquals(result, new Solution().combinationSum(new int[]{2, 3, 5}, 8));
    }
}
