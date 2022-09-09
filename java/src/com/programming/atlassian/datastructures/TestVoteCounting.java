package com.programming.atlassian.datastructures;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestVoteCounting {

    @Test
    public void testWinner() {
        VoteCounting voteCounting = new VoteCounting();
        List<String> winners = voteCounting.findWinner(List.of(new Vote("A", "B", "C"),
                new Vote("C", "B", "A"),
                new Vote("A", "B", "C"),
                new Vote("B", "A", "C")));

        Assert.assertEquals(List.of("B", "A", "C"), winners);
    }
}
