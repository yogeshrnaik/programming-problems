package com.programming.splitsms;

import java.util.Arrays;
import java.util.List;

public class Solution {

    public int solution(String S, int K) {
        return new SmsMessageSplitter(S, K).getSmsCount();
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
