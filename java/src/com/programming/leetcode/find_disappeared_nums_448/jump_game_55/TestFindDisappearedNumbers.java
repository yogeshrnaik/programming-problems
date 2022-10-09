package com.programming.leetcode.find_disappeared_nums_448.jump_game_55;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestFindDisappearedNumbers {

    @Test
    public void testFindDisappearedNumbers_sample1() {
        List<Integer> result = Arrays.asList(5, 6);
        Assert.assertEquals(result,
                new Solution().findDisappearedNumbers(new int[]{4, 3, 2, 7, 8, 2, 3, 1}));
    }

    @Test
    public void testFindDisappearedNumbers_sample2() {
        List<Integer> result = Arrays.asList(2);
        Assert.assertEquals(result,
                new Solution().findDisappearedNumbers(new int[]{1, 1}));
    }
}
