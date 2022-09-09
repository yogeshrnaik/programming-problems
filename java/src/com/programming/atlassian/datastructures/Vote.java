package com.programming.atlassian.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Vote {

    private String candidate1;
    private String candidate2;
    private String candidate3;

    public Vote(String candidate1) {
        this(candidate1, "", "");
    }

    public Vote(String candidate1, String candidate2) {
        this(candidate1, candidate2, "");
    }

    public Vote(String candidate1, String candidate2, String candidate3) {
        this.candidate1 = candidate1;
        this.candidate2 = candidate2;
        this.candidate3 = candidate3;
    }

    public List<String> getCandidates() {
        List<String> candidates = new ArrayList<>();
        candidates.add(candidate1);
        if (candidate2.length() > 0) candidates.add(candidate2);
        if (candidate3.length() > 0) candidates.add(candidate3);
        return candidates;
    }

}