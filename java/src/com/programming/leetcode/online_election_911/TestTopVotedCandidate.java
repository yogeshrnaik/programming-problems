package com.programming.leetcode.online_election_911;

import org.junit.Assert;
import org.junit.Test;

public class TestTopVotedCandidate {

    @Test
    public void testTopVotedCandidate_sample1() {
        int[] persons = new int[]{0, 1, 1, 0, 0, 1, 0};
        int[] times = new int[]{0, 5, 10, 15, 20, 25, 30};
        TopVotedCandidate topVotedCandidate = new TopVotedCandidate(persons, times);
        Assert.assertEquals(0, topVotedCandidate.q(3));
        Assert.assertEquals(1, topVotedCandidate.q(12));
        Assert.assertEquals(1, topVotedCandidate.q(25));
        Assert.assertEquals(0, topVotedCandidate.q(15));
        Assert.assertEquals(0, topVotedCandidate.q(24));
        Assert.assertEquals(1, topVotedCandidate.q(8));
    }

    @Test
    public void testTopVotedCandidate_sample2() {
        int[] persons = new int[]{0,0,0,0,1};
        int[] times = new int[]{0,6,39,52,75};
        TopVotedCandidate topVotedCandidate = new TopVotedCandidate(persons, times);
        Assert.assertEquals(0, topVotedCandidate.q(78));
        Assert.assertEquals(0, topVotedCandidate.q(99));
        Assert.assertEquals(0, topVotedCandidate.q(45));
        Assert.assertEquals(0, topVotedCandidate.q(49));
        Assert.assertEquals(0, topVotedCandidate.q(59));
        Assert.assertEquals(0, topVotedCandidate.q(68));
        Assert.assertEquals(0, topVotedCandidate.q(42));
        Assert.assertEquals(0, topVotedCandidate.q(37));
        Assert.assertEquals(0, topVotedCandidate.q(26));
        Assert.assertEquals(0, topVotedCandidate.q(43));
    }

}
