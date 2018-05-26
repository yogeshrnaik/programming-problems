package com.programming.splitsms;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SolutionWithStreamReduce extends Solution {

    private static int getSmsCount(String S, int limit) {
        List<String> words = Arrays.asList(S.split(" "));

        AtomicBoolean cannotBeDone = new AtomicBoolean(false);
        final AtomicInteger smsCount = new AtomicInteger(1);

        words.stream().reduce((s1, s2) -> {
            String sms = s1 + " " + s2;
            if (sms.length() >= limit) {
                smsCount.incrementAndGet();

                if (sms.length() == limit) {
                    return "";
                } else {
                    if (s2.length() > limit) {
                        cannotBeDone.set(true);
                    }
                    return s2;
                }

            }
            return sms;
        });

        if (words.size() == 1 && S.length() > limit) {
            cannotBeDone.set(true);
        }

        return cannotBeDone.get() ? -1 : smsCount.get();
    }

    @Override
    public int solution(String S, int K) {
        return getSmsCount(S, K);
    }

}
