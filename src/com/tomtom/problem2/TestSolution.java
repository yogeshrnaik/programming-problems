package com.tomtom.problem2;

import org.junit.Test;

public class TestSolution {

    private int result;

    @Test
    public void emptyOrNullOrOneElementArray() {
        Solution sol = new Solution();

        int result = sol.solution(null);
        assert result == Solution.NO_ADJACENT_INDICES;
        System.out.println(result);

        result = sol.solution(new int[]{});
        assert result == Solution.NO_ADJACENT_INDICES;
        System.out.println(result);


        result = sol.solution(new int[]{0});
        assert result == Solution.NO_ADJACENT_INDICES;
        System.out.println(result);
    }

    @Test
    public void twoElementArray_withDifferentValues() {
        Solution sol = new Solution();

        result = sol.solution(new int[]{0, 3});
        assert result == 3;
        System.out.println(result);

        result = sol.solution(new int[]{10, 3});
        assert result == 7;
        System.out.println(result);
    }

    @Test
    public void twoElementArray_withSameValues() {
        Solution sol = new Solution();

        result = sol.solution(new int[]{0, 0});
        assert result == 0;
        System.out.println(result);

        result = sol.solution(new int[]{2, 2});
        assert result == 0;
        System.out.println(result);
    }

    @Test
    public void distanceGt1000000000() {
        Solution sol = new Solution();

        result = sol.solution(new int[]{1000000000, 0});
        assert result == 1000000000;
        System.out.println(result);

        result = sol.solution(new int[]{1000000001, 0});
        assert result == Solution.DIFF_GT_MAX_DIFF_THRESHOLD;
        System.out.println(result);
    }

    @Test
    public void sampleGivenInProblem() {
        Solution sol = new Solution();

        result = sol.solution(new int[]{0, 3, 3, 7, 5, 3, 11, 1});
        assert result == 4;
        System.out.println(result);
    }
}
