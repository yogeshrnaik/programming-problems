package com.programming.splitsms;

import java.util.Arrays;
import java.util.List;

public class SolutionWithMerge extends Solution {

    private static class SmsResult {

        private int smsCount;
        private final int limit;
        private String smsSoFar;
        private boolean smsTxtBiggerThanLimit;

        public SmsResult(int limit) {
            this.limit = limit;
        }

        public SmsResult append(String txt) {
            if (smsTxtBiggerThanLimit || txt.length() > limit) {
                smsCount = -1;
                smsTxtBiggerThanLimit = true;
                return this;
            }

            String sms = (smsSoFar != null) ? smsSoFar + " " + txt : txt;
            if (sms.length() >= limit) {
                smsCount++;
                smsSoFar = sms.length() == limit ? null : txt;
            } else {
                smsSoFar = sms;
            }
            return this;
        }

        public SmsResult merge(SmsResult sms) {
            return this;
        }

        public int getSmsCount() {
            return smsSoFar != null && smsSoFar.trim().length() > 0 ? smsCount + 1 : smsCount;
        }

    }

    private static int getSmsCount(String S, int limit) {
        List<String> words = Arrays.asList(S.split(" "));

        SmsResult smsResult = words.stream().reduce(new SmsResult(limit),
            (sms, txt) -> sms.append(txt),
            (sms1, sms2) -> sms1.merge(sms2));

        return smsResult.getSmsCount();
    }

    @Override
    public int solution(String S, int K) {
        return getSmsCount(S, K);
    }

}
