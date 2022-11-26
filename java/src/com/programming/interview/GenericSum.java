package com.programming.interview;

import java.util.List;

public class GenericSum {
    public static void main(String[] args) {
        System.out.println(sum(List.of(1, 2, 3)));
        System.out.println(sum(List.of(1L, 2L, 3L)));
        System.out.println(sum(List.of(1.1, 2.2, 3.3)));
    }

    private static Double sum(List<? extends Number> nums) {
        double sum = 0;
        for (Number num : nums) {
            sum += num.doubleValue();
        }
        return sum;
    }
}
