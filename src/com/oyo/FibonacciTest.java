package com.oyo;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FibonacciTest {

    @Test
    public void testFibonacci() {
        assertEquals(4, CandidateCode.calculate_sum(0, 3));
        assertEquals(10857, CandidateCode.calculate_sum(10, 19));
    }
}
