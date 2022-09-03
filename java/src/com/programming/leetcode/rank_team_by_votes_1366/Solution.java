package com.programming.leetcode.rank_team_by_votes_1366;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/rank-teams-by-votes/
 */
class Solution {
    public String rankTeams(String[] votes) {
        return usingMap(votes);
    }

    private String using2dArray(String[] votes) {
        int[][] voteCount = new int[26][votes[0].length()];

        for (String vote : votes) {
            for (int i = 0; i < vote.length(); i++) {
                char team = vote.charAt(i);
                voteCount[team - 'A'][i]++;
            }
        }

        return new String(votes[0].toCharArray()).chars().mapToObj(c -> (char) c).sorted((team1, team2) -> {
            for (int i = 0; i < voteCount[0].length; i++) {
                if (voteCount[team1 - 'A'][i] != voteCount[team2 - 'A'][i]) {
                    return voteCount[team2 - 'A'][i] - voteCount[team1 - 'A'][i];
                }
            }
            return team1 - team2;
        }).map(Object::toString).collect(Collectors.joining());

    }

    private String usingMap(String[] votes) {
        Map<Character, int[]> ratings = new HashMap<>();
        for (String vote : votes) {
            for (int i = 0; i < vote.length(); i++) {
                char team = vote.charAt(i);
                ratings.putIfAbsent(team, new int[votes[0].length()]);
                ratings.get(team)[i]++;
            }
        }

        printMap(ratings);
        List<Map.Entry<Character, int[]>> entries = new LinkedList<>(ratings.entrySet());
        Collections.sort(entries, (e1, e2) -> {
            for (int i = 0; i < votes[0].length(); i++) {
                if (e1.getValue()[i] != e2.getValue()[i]) {
                    return e2.getValue()[i] - e1.getValue()[i];
                }
            }
            return e1.getKey() - e2.getKey();
        });
        return entries.stream().map(e -> String.valueOf(e.getKey())).collect(Collectors.joining());
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

    private static void printMap(Map<Character, int[]> ratings) {
        for (Map.Entry<Character, int[]> entry : ratings.entrySet()) {
            System.out.print(entry.getKey() + " ");
            for (int j = 0; j < entry.getValue().length; j++) {
                System.out.print(" " + entry.getValue()[j]);
            }
            System.out.println();
        }
        System.out.println("-------------------");
    }
}