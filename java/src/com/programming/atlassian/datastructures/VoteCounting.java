package com.programming.atlassian.datastructures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
We are going to implement a function that determines the winner of a competition. Our function is going to look something like this:

For a list of votes, return an ordered set of candidate in descending order of their votes.

List<String> findWinner(List<Vote> votes)

We pass in a list of votes and we are returned a list of names in the descending order of the score that each candidate received.

Assume that we extract the candidates' names from the votes as we process them.

A voter is allowed to vote for up to three different candidates. The order of the votes is important. The first vote that a voter places is worth three points. The second vote is worth two points. The third vote is worth one point.

The function should return a list of candidates in descending order of the total number of points received by the candidate.

Example input:
listOf(
Vote("A", "B", "C"),
Vote("C", "B", "A"),
Vote("A", "B", "C"),
Vote("B", "A", "C")
)
A B C D E F
Vote("A")
Vote("B", "A")
Vote("B", "A", "C")

Tie break:
If two people get same points, then the one with highest points first wins.
*/

//
//    Vote("A", "B", "C"), - A: 3 (1), B: 2 (2), C: 1(3) -> ABC
//    Vote("C", "B", "A"), - A: 4 (1, 3), B: 4 (2, 2), C: 4 (3, 1) -> CBA ->
//    Vote("A", "B", "C"), - A: 7 (1, 3, 1) B: 6 (2, 2, 2) C: 5 (3, 1, 3)
//    Vote("B", "A", "C")  - A: 9 (1,3,1,2) B: 9 (2,2,2,1), C:6 (3,1,3,3) -> BAC

// A: 3 + 1 + 3 + 2 = 9
// B: 2 + 2 + 2 + 3 = 9
// C: 1 + 3 + 1 + 1 = 6


public class VoteCounting {

    public List<String> findWinner(List<Vote> votes) {
        Map<String, Integer> voteCount = getVoteCount(votes);

        List<Map.Entry<String, Integer>> sorted = voteCount.entrySet().stream()
                .sorted((vc1, vc2) -> vc2.getValue() - vc1.getValue())
                .collect(Collectors.toList());

        return sorted.stream().map(vc -> vc.getKey()).collect(Collectors.toList());
    }

    private Map<String, Integer> getVoteCount(List<Vote> votes) {
        Map<String, Integer> voteCount = new HashMap<>();
        for (Vote vote : votes) {
            List<String> candidates = vote.getCandidates();
            for (int i = 0; i < candidates.size(); i++) {
                String candidate = candidates.get(i);
                int points = 3 - i;
                voteCount.put(candidate, points + voteCount.getOrDefault(candidates.get(i), 0));
            }
        }
        return voteCount;
    }
}