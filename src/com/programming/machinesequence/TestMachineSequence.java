package com.programming.machinesequence;

import org.junit.Test;

public class TestMachineSequence {

    @Test
    public void testSample() {
        assert "87".equals(CandidateCode.sequences("1,5,9,,2,3,,,5,6"));
        assert "0".equals(CandidateCode.sequences("1,5,9"));

        assert "10".equals(CandidateCode.sequences("10"));
        assert "10".equals(CandidateCode.sequences(" -5, 5"));

        assert "0".equals(CandidateCode.sequences("0,0,0,0,0,0,0,0,0,0,0,0,0"));
        assert "0".equals(CandidateCode.sequences("4,3,2,1,0"));

        assert "0".equals(CandidateCode.sequences("10,15,20"));

        assert "  ".equals(CandidateCode.sequences("  "));
        assert "10,12$".equals(CandidateCode.sequences("10,12$"));

    }
}
