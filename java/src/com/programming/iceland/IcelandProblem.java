package com.programming.iceland;

import java.util.Date;

public class IcelandProblem {

    public static void main(String[] args) {
        Date now = new Date();
        System.out.println(CandidateCode.criticalBridges(1, "({A,B,C},{(A,B),(B,C),(C,A)})"));
        // System.out.println(CandidateCode.criticalBridges(1, "({A,B,C,D,E,F},{(A,C),(B,C),(C,E),(B,E),(B,D),(E,F),(NA)}))"));
        // System.out.println(CandidateCode.criticalBridges(2,
        // "({A,B,C},{(A,B),(B,C),(C,A)}),({A,B,C,D,E,F},{(A,C),(B,C),(C,E),(B,E),(NA),(B,D),(E,F),(NA)}))"));

        // System.out.println(CandidateCode.criticalBridges(1, "({A,B,C,D,E,F},{(A,C),(B,C),(D,E),(E,F),(NA)}))"));
        // System.out.println(CandidateCode.criticalBridges(1, "({A,B,C,D,E,F},{(A,C),(B,C),(C,E),(B,E),(NA),(B,D),(E,F),(NA)}))"));
        // System.out.println(CandidateCode.criticalBridges(1, "({A,B,C,D,E,F},{(NA)}))"));

        // System.out.println(CandidateCode.criticalBridges(1,
        // "({A,B,C,D,E,F,G,H,I,J,K,L,M},{(A,C),(C,B),(B,D),(B,E),(B,F),(B,G),(B,H),(D,E),(D,J),(E,I),(E,F),(F,G),(G,H),(J,K),(J,M),(J,L)})"));

        // System.out.println(CandidateCode.criticalBridges(1, "({A,B,C,D},{(A,B),(A,C),(A,D),(B,C),(B,D),(C,D)})"));
        // System.out.println(CandidateCode.criticalBridges(1, "({A,B,C},{((NA))})"));
        // System.out.println(CandidateCode.criticalBridges(2,
        // "({A,B,C},{(A,B),(B,C),(C,A)}),({A,B,C,D,E},{(A,B),(B,C),(C,A),(E,D),(D,A)}))"));
        System.out.println("Time taken: = " + (System.currentTimeMillis() - now.getTime()));

    }

}
