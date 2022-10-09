package com.programming.leetcode.subarray_sum_equals_k;

import org.junit.Assert;
import org.junit.Test;

public class TestSubarraySumEqualsK {

    @Test
    public void testSubarraySumEqualsK_sample1() {
        Assert.assertEquals(2, new Solution().subarraySum(new int[]{1, 1, 1}, 2));
    }

    @Test
    public void testSubarraySumEqualsK_sample2() {
        Assert.assertEquals(2, new Solution().subarraySum(new int[]{1, 2, 3}, 3));
    }
}
