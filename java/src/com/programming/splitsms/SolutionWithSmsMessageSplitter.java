package com.programming.splitsms;

public class SolutionWithSmsMessageSplitter extends Solution {

    @Override
    public int solution(String S, int K) {
        return new SmsMessageSplitter(S, K).getSmsCount();
    }

}
