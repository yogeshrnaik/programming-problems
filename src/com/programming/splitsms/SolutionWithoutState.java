package com.programming.splitsms;

import java.util.Arrays;
import java.util.List;

public class SolutionWithoutState extends Solution {

    @Override
    public int solution(String S, int K) {
        return solution(K, Arrays.asList(S.split(" ")), "", 0);
    }

    private static int solution(final int limit, final List<String> remainingWords, final String smsSoFar, final int smsCountSoFar) {
        if (remainingWords.isEmpty()) {
            return smsSoFar.length() == 0 ? smsCountSoFar : smsCountSoFar + 1;
        }
        final String currWord = head(remainingWords);
        if (currWord.length() > limit) {
            return -1;
        }

        final String currSms = (smsSoFar + " " + currWord).trim();
        if (currSms.length() == limit) {
            return solution(limit, tail(remainingWords), "", smsCountSoFar + 1);
        }
        if (currSms.length() > limit) {
            return solution(limit, tail(remainingWords), currWord, smsCountSoFar + 1);
        }

        return solution(limit, tail(remainingWords), currSms, smsCountSoFar);
    }

    private static String head(List<String> list) {
        return list.get(0);
    }

    private static List<String> tail(List<String> list) {
        return list.subList(1, list.size());
    }
}
