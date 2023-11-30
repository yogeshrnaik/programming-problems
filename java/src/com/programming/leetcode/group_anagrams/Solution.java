package com.programming.leetcode.group_anagrams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        Map<String, List<String>> groupedAnagrams = new HashMap<String, List<String>>();
        for (String s : strs) {
            int[] freq = getLetterFreq(s);
            String k = toFreqString(freq);
            List<String> g = groupedAnagrams.getOrDefault(k, new ArrayList<>());
            g.add(s);
            groupedAnagrams.put(k, g);
        }

        for (List<String> anagrams : groupedAnagrams.values()) {
            result.add(anagrams);
        }
        return result;
    }

    private int[] getLetterFreq(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            int idx = (int) c - 97;
            freq[idx] = freq[idx] + 1;
        }
        return freq;
    }

    private String toFreqString(int[] freq) {
        StringBuilder sb = new StringBuilder();
        for(char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            sb.append(c).append(":").append(freq[(int) c - 97]).append(",");
        }
        return sb.toString();
    }
}