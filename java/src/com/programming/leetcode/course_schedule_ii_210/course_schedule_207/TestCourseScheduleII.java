package com.programming.leetcode.course_schedule_ii_210.course_schedule_207;

import org.junit.Assert;
import org.junit.Test;

public class TestCourseScheduleII {

    @Test
    public void testCourseScheduleII_sample1() {
        Assert.assertArrayEquals(new int[]{0, 1}, new Solution().findOrder(2, new int[][]{{1, 0}}));
    }

    @Test
    public void testCourseScheduleII_sample2() {
        Assert.assertArrayEquals(new int[0], new Solution().findOrder(2, new int[][]{{1, 0}, {0, 1}}));
    }
}
