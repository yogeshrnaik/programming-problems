package com.programming.booking.exam1.problem2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class Solution {

    private static class Interval implements Comparable<Interval> {

        private int start, end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Interval that) {
            return this.start - that.start;
        }
    }

    static void merge_overlapping_intervals() {
        try (Scanner s = new Scanner(System.in)) {

            List<Interval> intervals = new ArrayList<>();
            int intervalCount = s.nextInt();

            for (int h = 1; h <= intervalCount; h++) {
                int start = s.nextInt();
                int end = s.nextInt();
                intervals.add(new Interval(start, end));
            }

            Collections.sort(intervals);
            merge(intervals);
        }
    }

    private static void merge(List<Interval> intervals) {
        Stack<Interval> stack = new Stack<>();
        stack.push(intervals.get(0));
        for (int i = 1; i < intervals.size(); i++) {
            Interval top = stack.peek();
            if (top.end < intervals.get(i).start) // not overlapping
                stack.push(intervals.get(i));
            else if (top.end < intervals.get(i).end) { // overlapping
                top.end = intervals.get(i).end;
                stack.pop(); // remove top and push updated top value
                stack.push(top);
            }
        }

        Set<Interval> sorted = new TreeSet<>();
        while (!stack.isEmpty()) {
            sorted.add(stack.pop());
        }

        System.out.println(sorted.size());
        sorted.forEach(i -> System.out.println(i.start + " " + i.end));
    }

    public static void main(String[] args) {

        merge_overlapping_intervals();

    }
}
