package com.programming.splitsms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {

    private final Solution smsMessageSplitter = new SolutionWithSmsMessageSplitter();
    private final Solution splitWithStreamReduce = new SolutionWithStreamReduce();
    private final SolutionWithMerge splitWithMerge = new SolutionWithMerge();

    @Test
    public void testOne() {
        assertEquals(1, smsMessageSplitter.solution("A", 1));
        assertEquals(1, splitWithStreamReduce.solution("A", 1));
        assertEquals(1, splitWithMerge.solution("A", 1));
    }

    @Test
    public void testTwo() {
        assertEquals(2, smsMessageSplitter.solution("A B", 1));
        assertEquals(2, splitWithStreamReduce.solution("A B", 1));
        assertEquals(2, splitWithMerge.solution("A B", 1));
    }

    @Test
    public void testThree() {
        assertEquals(3, smsMessageSplitter.solution("A B C", 1));
        assertEquals(3, splitWithStreamReduce.solution("A B C", 1));
        assertEquals(3, splitWithMerge.solution("A B C", 1));
    }

    @Test
    public void testWordLargerThanSmsLength() {
        assertEquals(-1, smsMessageSplitter.solution("AB", 1));
        assertEquals(-1, smsMessageSplitter.solution("A AB", 1));

        assertEquals(-1, splitWithStreamReduce.solution("AB", 1));
        assertEquals(-1, splitWithStreamReduce.solution("A AB", 1));

        assertEquals(-1, splitWithMerge.solution("AB", 1));
        assertEquals(-1, splitWithMerge.solution("A AB", 1));
    }

    @Test
    public void testFiveAsSmsLength() {
        assertEquals(5, smsMessageSplitter.solution("Hi there how are you", 5));
        assertEquals(5, splitWithStreamReduce.solution("Hi there how are you", 5));
        assertEquals(5, splitWithMerge.solution("Hi there how are you", 5));
    }

    @Test
    public void testSevenAsSmsLength() {
        assertEquals(4, smsMessageSplitter.solution("Hi there how are you", 7));
        assertEquals(4, splitWithStreamReduce.solution("Hi there how are you", 7));
        assertEquals(4, splitWithMerge.solution("Hi there how are you", 7));
    }

    @Test
    public void testEightAsSmsLength() {
        assertEquals(3, smsMessageSplitter.solution("Hi there how are you", 8));
        assertEquals(3, splitWithStreamReduce.solution("Hi there how are you", 8));
        assertEquals(3, splitWithMerge.solution("Hi there how are you", 8));
    }

    @Test
    public void testBig() {
        assertEquals(12, smsMessageSplitter.solution("Hello there, my name is not importnant right now."
            + " I am just simple sentecne used to test few things.", 10));
        assertEquals(12, splitWithStreamReduce.solution("Hello there, my name is not importnant right now."
            + " I am just simple sentecne used to test few things.", 10));
        assertEquals(12, splitWithMerge.solution("Hello there, my name is not importnant right now."
            + " I am just simple sentecne used to test few things.", 10));
    }

}
