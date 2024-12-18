package com.programming.agoda;

public class MinimumDeletions {
    public static int minDeletions(String s) {
        // Initialize the count for deletions
        int deletions = 0;

        // Loop through the string and check for adjacent similar characters
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                deletions++; // Increment deletions count if adjacent characters are the same
            }
        }

        return deletions;
    }

    public static void main(String[] args) {
        String s = "abbbbcdb";
        System.out.println("Minimum deletions: " + minDeletions(s));
    }
}
