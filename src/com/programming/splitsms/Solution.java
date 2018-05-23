package com.programming.splitsms;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {

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

            String sms = smsSoFar + " " + txt;
            if (sms.length() > limit) {
                smsCount++;
                smsSoFar = txt;
            } else {
                smsSoFar = smsSoFar + " " + txt;
            }
            return this;
        }

        public SmsResult merge(SmsResult sms) {
            return this;
        }

    }

    private static int getSmsCount(String S, int limit) {
        List<String> words = Arrays.asList(S.split(" "));

        SmsResult smsResult = words.stream().reduce(new SmsResult(limit),
            (sms, txt) -> sms.append(txt),
            (sms1, sms2) -> sms1.merge(sms2));

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

    public int solution(String S, int K) {
        return getSmsCount(S, K);
        // return new SmsMessageSplitter(S, K).getSmsCount();
    }

    private static final class SmsMessageSplitter {

        private final String fullText;
        private final int limit;
        private List<String> words;
        String currSMS = "";
        int smsCount = 0;

        public SmsMessageSplitter(String S, int K) {
            words = Arrays.asList(S.split(" "));
            this.fullText = S;
            this.limit = K;
        }

        public int getSmsCount() {
            if (fullText.length() <= limit) {
                return 1;
            }

            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i);

                if (word.length() > limit) {
                    return -1;
                }

                currSMS += word + " ";
                if (currSMS.trim().length() >= limit) {
                    countSMS(word);
                }
            }

            if (currSMS.trim().length() > limit) {
                return -1;
            }

            if (currSMS.trim().length() > 0) {
                smsCount++;
            }

            return smsCount;

        }

        private void countSMS(String word) {
            smsCount++;
            if (currSMS.trim().length() == limit) {
                currSMS = ""; // reset SMS
            } else {
                currSMS = word + " ";
            }
        }
    }
}
