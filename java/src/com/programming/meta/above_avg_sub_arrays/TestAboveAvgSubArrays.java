package com.programming.meta.above_avg_sub_arrays;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAboveAvgSubArrays {

    @Test
    public void sample1() {
        int arr[] = {6, 3, 5};
        int count = Solution.countSubArrays(arr, arr.length);
        assertEquals(3, count);
    }


}
