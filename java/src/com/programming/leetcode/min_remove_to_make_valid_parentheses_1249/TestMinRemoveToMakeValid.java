package com.programming.leetcode.min_remove_to_make_valid_parentheses_1249;

import org.junit.Assert;
import org.junit.Test;

public class TestMinRemoveToMakeValid {

    @Test
    public void testMinRemove() {
        Assert.assertEquals("lee(t(c)o)de", new Solution().minRemoveToMakeValid("lee(t(c)o)de)"));
        Assert.assertEquals("ab(c)d", new Solution().minRemoveToMakeValid("a)b(c)d"));
        Assert.assertEquals("", new Solution().minRemoveToMakeValid("))(("));
    }
}
