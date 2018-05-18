package com.programming.politics;

import org.junit.Test;

public class TestPoliticalPartyComparison {

    @Test
    public void testInvalidInput() {
        String result = CandidateCode.partiescompare(new int[] {12, 11, 5, 2, 7, 5, -11}, new int[] {5, 12, 5, 7, 11, 2, 11});
        System.out.println(result);
    }

    @Test
    public void testSampleInput() {
        String result = CandidateCode.partiescompare(new int[] {12, 11, 5, 2, 7, 5, 11}, new int[] {5, 12, 5, 7, 11, 2, 11});
        System.out.println(result);
    }

    @Test
    public void testUnequal() {
        String result = CandidateCode.partiescompare(new int[] {12, 11, 5, 2, 7, 5, 11}, new int[] {5, 0, 5, 7, 11, 2, 11});
        System.out.println(result);
    }
}
