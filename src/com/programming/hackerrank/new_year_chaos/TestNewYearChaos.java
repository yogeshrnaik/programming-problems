package com.programming.hackerrank.new_year_chaos;

import org.junit.Test;

public class TestNewYearChaos {

    @Test
    public void testSample() {
        Solution.minimumBribes(new int[]{1, 2, 5, 3, 7, 8, 6, 4});
    }

// 1, 2, 3, 4, 5, 6, 7, 8
// 1, 2, 5, 3, 4, 6, 7, 8 - bribes = 2
// 1, 2, 5, 3, 7, 4, 6, 8 - bribes = 2
// 1, 2, 5, 3, 7, 8, 4, 6 - bribes = 2
// 1, 2, 5, 3, 7, 8, 6, 4 - bribes = 1
}
