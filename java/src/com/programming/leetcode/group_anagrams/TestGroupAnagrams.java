package com.programming.leetcode.group_anagrams;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestGroupAnagrams {

    @Test
    public void testGroupAnagrams_sample1() {
        List<List<String>> result = Arrays.asList(Arrays.asList("bat"), Arrays.asList("tan","nat"), Arrays.asList("eat","tea","ate"));
        Assert.assertEquals(result,
                new Solution().groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
    }
    @Test
    public void testGroupAnagrams_sample2() {
        List<List<String>> result = Arrays.asList(Arrays.asList(""));
        Assert.assertEquals(result,
                new Solution().groupAnagrams(new String[]{""}));
    }
    @Test
    public void testGroupAnagrams_sample3() {
        List<List<String>> result = Arrays.asList(Arrays.asList("a"));
        Assert.assertEquals(result,
                new Solution().groupAnagrams(new String[]{"a"}));
    }
    @Test
    public void testGroupAnagrams_sample4() {
        List<List<String>> result = Arrays.asList(Arrays.asList("bbbbbbbbbbc"), Arrays.asList("bdddddddddd"));
        Assert.assertEquals(result,
                new Solution().groupAnagrams(new String[]{"bdddddddddd", "bbbbbbbbbbc"}));
    }

}
