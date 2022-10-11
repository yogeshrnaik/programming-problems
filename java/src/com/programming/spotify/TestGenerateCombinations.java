package com.programming.spotify;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGenerateCombinations {

    @Test
    public void generateCombination_null() {
        Solution sol = new Solution();
        List<String> result = sol.generateCombinations(null);
        Assert.assertEquals(Arrays.asList(""), result);
    }

    @Test
    public void generateCombination_singleList() {
        Solution sol = new Solution();
        List<List<String>> input = new ArrayList<>();
        input.add(Arrays.asList("a", "b"));
        List<String> result = sol.generateCombinations(input);
        Assert.assertEquals(Arrays.asList("a", "b"), result);
    }

    @Test
    public void generateCombination_twoLists_withMultipleElement() {
        Solution sol = new Solution();
        List<List<String>> input = new ArrayList<>();
        input.add(Arrays.asList("a", "b"));
        input.add(Arrays.asList("c", "d"));
        List<String> result = sol.generateCombinations(input);
        Assert.assertEquals(Arrays.asList("ac", "ad", "bc", "bd"), result);
    }

    @Test
    public void generateCombination_threeLists_withMultipleElement() {
        Solution sol = new Solution();
        List<List<String>> input = new ArrayList<>();
        input.add(Arrays.asList("a", "b"));
        input.add(Arrays.asList("c"));
        input.add(Arrays.asList("d", "e"));
        List<String> result = sol.generateCombinations(input);
        Assert.assertEquals(Arrays.asList("acd", "ace", "bcd", "bce"), result);
    }

}
