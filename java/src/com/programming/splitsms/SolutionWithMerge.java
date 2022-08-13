package com.programming.splitsms;

import java.util.Arrays;
import java.util.List;

public class SolutionWithMerge extends Solution {

    private static class SmsResult {

        private final int limit;
        private int smsCount;
        private String smsSoFar;
        private boolean smsTxtBiggerThanLimit;

        public SmsResult(int limit) {
            this.limit = limit;
            smsCount = 0;
            smsSoFar = "";
            smsTxtBiggerThanLimit = false;
        }

        public SmsResult append(String txt) {
            if (isSmsBiggerThanLimit(txt)) {
                smsCount = -1;
                smsTxtBiggerThanLimit = true;
                return this;
            }
            return appendText(txt);
        }

        private boolean isSmsBiggerThanLimit(String txt) {
            return smsTxtBiggerThanLimit || txt.length() > limit;
        }

        private SmsResult appendText(String txt) {
            String currSms = isSmsSoFarNotEmpty() ? smsSoFar + " " + txt : txt;
            if (currSms.length() >= limit) {
                smsCount++;
                smsSoFar = currSms.length() == limit ? "" : txt;
            } else {
                smsSoFar = currSms;
            }
            return this;
        }

        private boolean isSmsSoFarNotEmpty() {
            return smsSoFar != null && smsSoFar.trim().length() > 0;
        }

        public int getSmsCount() {
            return isSmsSoFarNotEmpty() ? smsCount + 1 : smsCount;
        }

    }

    private static int getSmsCount(String S, int limit) {
        List<String> words = Arrays.asList(S.split(" "));

        SmsResult smsResult = words.stream().reduce(new SmsResult(limit),
            (sms, txt) -> sms.append(txt),
            (sms1, sms2) -> null);

        return smsResult.getSmsCount();
    }

    @Override
    public int solution(String S, int K) {
        return getSmsCount(S, K);
    }

}
