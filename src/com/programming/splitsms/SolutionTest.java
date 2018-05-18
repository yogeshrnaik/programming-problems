package com.programming.splitsms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {

    private final Solution sol = new Solution();

    @Test
    public void testOne() {
        assertEquals(1, sol.solution("A", 1));
    }

    @Test
    public void testTwo() {
        assertEquals(2, sol.solution("A B", 1));
    }

    @Test
    public void testThree() {
        assertEquals(3, sol.solution("A B C", 1));
    }

    @Test
    public void testWordLargerThanSmsLength() {
        assertEquals(-1, sol.solution("AB", 1));
        assertEquals(-1, sol.solution("A AB", 1));
    }

    @Test
    public void testFiveAsSmsLength() {
        assertEquals(5, sol.solution("Hi there how are you", 5));
    }

    @Test
    public void testSevenAsSmsLength() {
        assertEquals(4, sol.solution("Hi there how are you", 7));
    }

    @Test
    public void testEightAsSmsLength() {
        assertEquals(3, sol.solution("Hi there how are you", 8));
    }

    @Test
    public void testBig() {
        assertEquals(12, sol.solution("Hello there, my name is not importnant right now."
            + " I am just simple sentecne used to test few things.", 10));
    }

}
