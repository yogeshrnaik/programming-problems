package com.programming.spotify;

import org.junit.Assert;
import org.junit.Test;
import com.programming.spotify.Solution;

import java.util.ArrayList;
import java.util.List;

public class TestGenerateCombinations {

    @Test
    public void generateCombination_null() {
        Solution sol = new Solution();
        List<String> result = sol.generateCombinations(null);
        Assert.assertEquals(createList(""), result);
    }

    @Test
    public void generateCombination_singleList() {
        Solution sol = new Solution();
        List<List<String>> input = new ArrayList<>();
        input.add(createList("a", "b"));
        List<String> result = sol.generateCombinations(input);
        Assert.assertEquals(createList("a", "b"), result);
    }

    @Test
    public void generateCombination_twoLists_withMultipleElement() {
        Solution sol = new Solution();
        List<List<String>> input = new ArrayList<>();
        input.add(createList("a", "b"));
        input.add(createList("c", "d"));
        List<String> result = sol.generateCombinations(input);
        Assert.assertEquals(createList("ac", "ad", "bc", "bd"), result);
    }

    @Test
    public void generateCombination_threeLists_withMultipleElement() {
        Solution sol = new Solution();
        List<List<String>> input = new ArrayList<>();
        input.add(createList("a", "b"));
        input.add(createList("c"));
        input.add(createList("d", "e"));
        List<String> result = sol.generateCombinations(input);
        Assert.assertEquals(createList("acd", "ace", "bcd", "bce"), result);
    }

    private List<String> createList(String... values) {
        List<String> result = new ArrayList<>();
        for (String v : values) {
            result.add(v);
        }
        return result;
    }
}
