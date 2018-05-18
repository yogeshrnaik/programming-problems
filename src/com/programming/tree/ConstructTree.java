package com.programming.tree;

import org.junit.Test;

public class ConstructTree {

    @Test
    public void testSampleInput() {
        String sampleOutput = "0111011110101101000110";
        String result = CandidateCode.constructTree("acdabebaae");
        System.out.println(result);
        assert result.equals(sampleOutput);
    }

    @Test
    public void testOwnTestCases() {
        String sampleOutput = "0";
        String result = CandidateCode.constructTree("a");
        System.out.println(result);
        assert result.equals(sampleOutput);

        sampleOutput = "00";
        result = CandidateCode.constructTree("aa");
        System.out.println(result);
        assert result.equals(sampleOutput);

        sampleOutput = "10";
        result = CandidateCode.constructTree("ba");
        System.out.println(result);
        assert result.equals(sampleOutput);

        sampleOutput = "00";
        result = CandidateCode.constructTree("bb");
        System.out.println(result);
        assert result.equals(sampleOutput);

        sampleOutput = "100";
        result = CandidateCode.constructTree("abb");
        System.out.println(result);
        assert result.equals(sampleOutput);
    }
}
