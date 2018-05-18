package com.programming.stone;

import org.junit.Test;

public class StoneCollectionProblem {

    @Test
    public void testInvalidInput() {
        assert -1 == CandidateCode.ThirstyCrowProblem(null, 0, 0);
        assert -1 == CandidateCode.ThirstyCrowProblem(null, 0, 1);
        assert -1 == CandidateCode.ThirstyCrowProblem(null, 1, 0);
        assert -1 == CandidateCode.ThirstyCrowProblem(null, 2, 1);
        assert -1 == CandidateCode.ThirstyCrowProblem(new int[] {1, 2}, 0, 0);

        assert -1 == CandidateCode.ThirstyCrowProblem(new int[] {1, 2}, -1, -1);
        assert -1 == CandidateCode.ThirstyCrowProblem(new int[] {1, 2}, -1, 0);
        assert -1 == CandidateCode.ThirstyCrowProblem(new int[] {1, 2}, 0, -1);

        assert -1 == CandidateCode.ThirstyCrowProblem(new int[] {-1, -2}, 2, 1);
    }

    @Test
    public void testTotalPot1And1PotToOverflow() {
        // System.out.println(CandidateCode.ThirstyCrowProblem(new int[]{1,2}, 2, 1));
        assert 100 == CandidateCode.ThirstyCrowProblem(new int[] {100}, 1, 1);
        assert 58 == CandidateCode.ThirstyCrowProblem(new int[] {58}, 1, 1);
    }

    @Test
    public void testTotalPots2And1PotToOverflow() {
        // System.out.println(CandidateCode.ThirstyCrowProblem(new int[]{1,2}, 2, 1));
        assert 2 == CandidateCode.ThirstyCrowProblem(new int[] {1, 2}, 2, 1);
        assert 10 == CandidateCode.ThirstyCrowProblem(new int[] {58, 5}, 2, 1);
        assert 10 == CandidateCode.ThirstyCrowProblem(new int[] {5, 58}, 2, 1);
        assert 9 == CandidateCode.ThirstyCrowProblem(new int[] {5, 9}, 2, 1);
        assert 8 == CandidateCode.ThirstyCrowProblem(new int[] {5, 8}, 2, 1);
    }

    @Test
    public void testAllPotsHaveSameSize() {
        assert 2 == CandidateCode.ThirstyCrowProblem(new int[] {2, 2}, 2, 1);
        assert 4 == CandidateCode.ThirstyCrowProblem(new int[] {2, 2}, 2, 2);

        assert 3 == CandidateCode.ThirstyCrowProblem(new int[] {3, 3, 3, 3, 3}, 5, 1);
        assert 6 == CandidateCode.ThirstyCrowProblem(new int[] {3, 3, 3, 3, 3}, 5, 2);
        assert 24 == CandidateCode.ThirstyCrowProblem(new int[] {4, 4, 4, 4, 4, 4, 4}, 7, 6);

    }

    @Test
    public void testAllPotsToOverflow() {
        assert 63 == CandidateCode.ThirstyCrowProblem(new int[] {58, 5}, 2, 2);
        assert 701 == CandidateCode.ThirstyCrowProblem(new int[] {1, 500, 200}, 3, 3);
        assert 165 == CandidateCode.ThirstyCrowProblem(new int[] {10, 20, 35, 100}, 4, 4);

    }

    @Test
    public void testParseInputAndCreateDataStructure() {
        CandidateCode.parseInputAndCreateDataStructure(new int[] {58, 5}, 2, 2);
        CandidateCode.parseInputAndCreateDataStructure(new int[] {1, 500, 200}, 3, 3);
        CandidateCode.parseInputAndCreateDataStructure(new int[] {10, 20, 35, 100}, 4, 4);

    }

    @Test
    public void testActualCases() {
        int result = -1;

        result = CandidateCode.ThirstyCrowProblem(new int[] {5, 6, 8, 107}, 4, 1);
        assert 16 == result; // should be 16
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {5, 6, 10, 107}, 4, 1);
        assert 20 == result; // should be 20
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {5, 6, 7, 107}, 4, 1);
        assert 14 == result; // should be 14
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {5, 6, 7, 107}, 4, 2);
        assert 20 == result; // should be 14 + 6 = 20
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {5, 100, 106, 107}, 4, 1);
        assert 20 == result; // should be 20
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {5, 100, 106, 107}, 4, 2);
        assert 20 + 102 == result; // should be 4 * 5 + (107 - 5) = 20 + 102 == 122
        System.out.println(CandidateCode.ThirstyCrowProblem(new int[] {5, 100, 106, 107}, 4, 2));

        result = CandidateCode.ThirstyCrowProblem(new int[] {1, 1001, 1002}, 3, 1);
        assert 3 == result; // should be 3
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {1, 1001, 1002}, 3, 2);
        assert 1004 == result; // should be 3 + (1002 - 1) = 1004
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {1000, 1001, 1002}, 3, 1);
        assert 1002 == result;
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {1000, 1001, 1002}, 3, 2);
        assert 1002 + 1001 == result;
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {1000, 1001, 1002}, 3, 3);
        assert 3003 == result;
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {5, 6, 7, 8, 9, 10}, 6, 1);
        assert 10 == result;
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {5, 6, 7, 8, 9, 10}, 6, 2);
        assert 10 + 9 == result;
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {5, 6, 7, 8, 9, 10}, 6, 3);
        assert 10 + 9 + 8 == result;
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {2, 15, 26, 60}, 4, 1);
        assert 8 == result;
        System.out.println(result);

        result = CandidateCode.ThirstyCrowProblem(new int[] {2, 15, 26, 60}, 4, 2);
        assert 8 + 39 == result;
        System.out.println(result); // should be 8 + 3 * 13 = 8 + 39 = 47
        // System.out.println(CandidateCode.ThirstyCrowProblem(new int[]{2, 15, 26, 60}, 4, 3)); // should be ???? (8 + 2 * 13 + 11 + 13) =
        // 58

        // System.out.println(CandidateCode.ThirstyCrowProblem(new int[]{1, 9, 27, 29}, 4, 1)); // 4
        // System.out.println(CandidateCode.ThirstyCrowProblem(new int[]{1, 9, 27, 29}, 4, 2)); // 28

        // System.out.println(CandidateCode.ThirstyCrowProblem(new int[]{2, 2, 2, 3}, 4, 1)); // should be 3
        // System.out.println(CandidateCode.ThirstyCrowProblem(new int[]{2, 2, 2, 3}, 4, 2)); // should be 5

        // System.out.println(CandidateCode.ThirstyCrowProblem(new int[]{2, 2, 3, 4}, 4, 3)); // should be 9

        // System.out.println(CandidateCode.ThirstyCrowProblem(new int[]{2, 2, 2, 3}, 4, 3)); // should be 7
    }
}
