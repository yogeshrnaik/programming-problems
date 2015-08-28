package com.gcd;

import org.junit.Test;

import java.util.Arrays;

public class TestGcdProblem {

    @Test
    public void testExtendedEuclid() {
        System.out.println(Arrays.toString(CandidateCode.coins_value(new int[] {25, 45})));
        System.out.println(Arrays.toString(CandidateCode.coins_value(new int[] {2000, 55})));
        System.out.println(Arrays.toString(CandidateCode.coins_value(new int[] {3000, 55})));
        System.out.println(Arrays.toString(CandidateCode.coins_value(new int[] {55, 2000})));
        System.out.println(Arrays.toString(CandidateCode.coins_value(new int[] {55, 3000})));
    }
}
