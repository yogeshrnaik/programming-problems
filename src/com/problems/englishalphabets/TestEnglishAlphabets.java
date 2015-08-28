package com.problems.englishalphabets;

import org.junit.Test;

public class TestEnglishAlphabets {

    @Test
    public void testCase1() {
        int input1 = 2;
        String[] input2 = {"A#S", "S#T"};
        String input3 = "AS";
        assert 2 == CandidateCode.word_count(input1, input2, input3);
    }

}
