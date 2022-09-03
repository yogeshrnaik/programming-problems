package com.programming.leetcode.rank_team_by_votes_1366;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/rank-teams-by-votes/
 */
class Solution {
    public String rankTeams(String[] votes) {
        int[][] voteCount = new int[26][votes[0].length()];
        for (String vote : votes) {
            for (int i = 0; i < vote.length(); i++) {
                char team = vote.charAt(i);
                voteCount[team - 'A'][i]++;
            }
        }

        return new String(votes[0].toCharArray()).chars().mapToObj( c -> (char)c).sorted((team1, team2) -> {
            for (int i=0; i<voteCount[0].length; i++) {
                if (voteCount[team1-'A'][i] != voteCount[team2-'A'][i]) {
                    return voteCount[team2-'A'][i] - voteCount[team1-'A'][i];
                }
            }
            return team1 - team2;
        }).map(Object::toString).collect(Collectors.joining());
    }

    private static void print(int[][] voteCount) {
        for (int i = 0; i < voteCount.length; i++) {
            System.out.print((char) ('A' + i));
            for (int j = 0; j < voteCount[0].length; j++) {
                System.out.print(" " + voteCount[i][j]);
            }
            System.out.println();
        }
    }
}