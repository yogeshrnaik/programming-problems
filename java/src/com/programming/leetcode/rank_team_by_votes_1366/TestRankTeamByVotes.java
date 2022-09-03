package com.programming.leetcode.rank_team_by_votes_1366;

import org.junit.Assert;
import org.junit.Test;

public class TestRankTeamByVotes {


    @Test
    public void testRankTeamByVotes() {
        Assert.assertEquals("ACB", new Solution().rankTeams(new String[]{"ABC", "ACB", "ABC", "ACB", "ACB"}));
        Assert.assertEquals("XWYZ", new Solution().rankTeams(new String[]{"WXYZ", "XYZW"}));
    }

}
