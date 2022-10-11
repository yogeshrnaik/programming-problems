package com.programming.leetcode.valid_palindrome_ii_680;

import org.junit.Assert;
import org.junit.Test;

public class TestValidPalindromeII {

    @Test
    public void testValidPalindromeII() {
        Assert.assertTrue(new Solution().validPalindrome("aba"));
        Assert.assertTrue(new Solution().validPalindrome("abca"));
        Assert.assertFalse(new Solution().validPalindrome("abc"));
    }
}
