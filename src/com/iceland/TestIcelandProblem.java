package com.iceland;

import com.iceland.CandidateCode;
import org.junit.Test;


public class TestIcelandProblem {

    @Test
    public void testIcelandProblem() {
        assert "{{NA},{(A,D),(D,E)}}".equals(CandidateCode.criticalBridges(2, "({A,B,C},{(A,B),(B,C),(C,A)}),({A,B,C,D,E},{(A,B),(B,C),(C,A),(E,D),(D,A)}))"));
        assert "{(A,C),(B,C),(D,J),(E,I),(J,K),(J,L),(J,M)}".equals(CandidateCode.criticalBridges(1, "({A,B,C,D,E,F,G,H,I,J,K,L,M},{(A,C),(C,B),(B,D),(B,E),(B,F),(B,G),(B,H),(D,E),(D,J),(E,I),(E,F),(F,G),(G,H),(J,K),(J,M),(J,L)})"));
        assert "{(A,C),(B,D),(E,F)}".equals(CandidateCode.criticalBridges(1, "({A,B,C,D,E,F},{(A,C),(B,C),(C,E),(B,E),(B,D),(E,F)}))"));
        assert "{NA}".equals(CandidateCode.criticalBridges(1, "({A,B,C,D},{(A,B),(A,C),(A,D),(B,C),(B,D),(C,D)})"));
    }
}
