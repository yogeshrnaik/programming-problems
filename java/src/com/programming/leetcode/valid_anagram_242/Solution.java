package com.programming.leetcode.valid_anagram_242;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean isAnagram(String s, String t) {
        Map<Character, Integer> sCount = new HashMap<>();
        Map<Character, Integer> tCount = new HashMap<>();

        for (Character c : s.toCharArray()) {
            sCount.put(c, 1 + sCount.getOrDefault(c, 0));
        }
        for (Character c : t.toCharArray()) {
            tCount.put(c, 1 + tCount.getOrDefault(c, 0));
        }
        for (Character c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            if (!sCount.getOrDefault(c, 0).equals(tCount.getOrDefault(c, 0))) {
                return false;
            }
        }

        return true;
    }
}