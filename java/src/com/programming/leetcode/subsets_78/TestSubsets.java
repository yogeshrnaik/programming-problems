package com.programming.leetcode.subsets_78;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestSubsets {

    @Test
    public void testSubsets_sample1() {
        List<List<Integer>> expected = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(1, 2),
                Arrays.asList(1, 3),
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(2),
                Arrays.asList(3),
                Arrays.asList()

        );
        List<List<Integer>> actual = new Solution().subsets(new int[]{1, 2, 3});
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSubsets_sample2() {
        List<List<Integer>> expected = Arrays.asList(
                Arrays.asList(0),
                Arrays.asList()

        );
        List<List<Integer>> actual = new Solution().subsets(new int[]{0});
        Assert.assertEquals(expected, actual);
    }
}
