package com.programming.leetcode.min_remove_to_make_valid_parentheses_1249;

import java.util.*;

// Problem: https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
public class Solution {
    public String minRemoveToMakeValid(String s) {
        Stack<Integer> stack = new Stack<>();
        Set<Integer> indicesToRemove = new HashSet<>();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            if (curr == '(') stack.push(i);
            else if (curr == ')') {
                if (stack.isEmpty()) {
                    indicesToRemove.add(i);
                } else {
                    stack.pop();
                }
            }
        }

        while (!stack.isEmpty()) {
            indicesToRemove.add(stack.pop());
        }

        for (int i = 0; i < s.length(); i++) {
            if (indicesToRemove.contains(i)) continue;
            result.append(s.charAt(i));
        }

        return result.toString();
    }
}