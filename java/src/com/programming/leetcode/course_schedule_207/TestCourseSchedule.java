package com.programming.leetcode.course_schedule_207;

import org.junit.Assert;
import org.junit.Test;

public class TestCourseSchedule {

    @Test
    public void testCourseSchedule_sample1() {
        Assert.assertTrue(new Solution().canFinish(2, new int[][]{{1, 0}}));
    }

    @Test
    public void testCourseSchedule_sample2() {
        Assert.assertFalse(new Solution().canFinish(2, new int[][]{{1, 0}, {0, 1}}));
    }
}
