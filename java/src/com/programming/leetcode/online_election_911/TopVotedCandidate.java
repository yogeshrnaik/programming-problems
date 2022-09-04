package com.programming.leetcode.online_election_911;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Problem: https://leetcode.com/problems/online-election/
*/
public class TopVotedCandidate {

    private int[] times;
    private List<Integer> leaders;

    public TopVotedCandidate(int[] persons, int[] times) {
        this.times = times;
        this.leaders = new ArrayList<>();
        int leader = 0;
        Map<Integer, Integer> voteCount = new HashMap<>();

        for (int person : persons) {
            voteCount.put(person, 1 + voteCount.getOrDefault(person, 1));
            if (voteCount.get(person) >= voteCount.getOrDefault(leader, 0)) {
                leader = person;
            }
            leaders.add(leader);
        }
    }

    public int q(int t) {
        return this.leaders.get(binarySearch(t));
    }

    private int binarySearch(int t) {
        int left = 0;
        int right = this.times.length - 1;
        int resultIndex = -1;
        while (left <= right) {
            int middle = (left + right) / 2;
            int middleTime = times[middle];
            if (middleTime == t) {
                return middle;
            } else if (middleTime < t) {
                resultIndex = Math.max(middle, resultIndex);
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return resultIndex;
    }
}

/**
 * Your TopVotedCandidate object will be instantiated and called as such:
 * TopVotedCandidate obj = new TopVotedCandidate(persons, times);
 * int param_1 = obj.q(t);
 */