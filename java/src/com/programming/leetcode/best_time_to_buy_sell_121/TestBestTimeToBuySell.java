package com.programming.leetcode.best_time_to_buy_sell_121;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestBestTimeToBuySell {

    @Test
    public void testMaxProfit() {
        Assert.assertEquals(5, new Solution().maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        Assert.assertEquals(0, new Solution().maxProfit(new int[]{7, 6, 4, 3, 1}));
    }
}
