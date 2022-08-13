package com.programming.balancelog;

import java.util.Scanner;

public class BalanceLog {

    // public static void main(String[] args) {
    // System.out.println("Hello World");
    // // int[] a = {1,2,3,7,6,5,9,5,6,7,5,2,-1};//expected 6th position
    // int[] a = {1, 2, 9, 4, -1};
    // System.out.println("(Best) Balance point for a is index " + BalanceBest(a));
    // }

    public static int BalanceBest(int[] a) {
        int leftSum = a[0];
        int rightSum = 0;
        for (int i = 0; i < a.length; i++)// notice we start from 2nd as 1st value is set
            rightSum += a[i];// each sum is sum of previous sum plus current value

        for (int i = 0; i < a.length - 1; i++) {
            if (leftSum == rightSum)
                return i;
            leftSum += a[i + 1];
            rightSum -= a[i];
        }
        return -1;// otherwise we return -1 as not found
    }

    public double balancePoint(Log log) {
        // YOUR CODE HERE

        double leftSum = log.weightUpto(1);
        double rightSum = 0;
        for (int i = 1; i < log.length(); i++)
            rightSum += log.weightUpto(i);// each sum is sum of previous sum plus current value

        for (int i = 0; i < log.length() - 1; i++) {
            if (leftSum == rightSum)
                return i;
            leftSum += log.weightUpto(i + 1);
            rightSum -= log.weightUpto(i);
        }
        return -1;// otherwise we return -1

    }

    // DO NOT MODIFY CODE BELOW THIS LINE
    interface Log {

        double weightUpto(double x); // returns the weightUpto of the part of the log from the left end to a point at distance x from it.

        double length(); // returns the total length of the log
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split(" ");
            Log c = null;
            switch (tokens[0]) {
                case "LINE":
                    c = new Line(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]),
                        Double.parseDouble(tokens[3]));
                    break;
                case "EXP":
                    c = new Exp(Double.parseDouble(tokens[1]));
                    break;
                case "POWER":
                    c = new Power(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
                    break;
                case "SINE":
                    c = new Sine(Double.parseDouble(tokens[1]));
                    break;
            }

            if (c == null) {
                break;
            }
            BalanceLog t = new BalanceLog();
            double h = t.balancePoint(c);
            System.out.println(Math.round(h * 1000d));
        }

        scanner.close();
    }

    static class Line implements Log {

        private double m;
        private double c;
        private double l;

        public Line(double l, double m, double c) {
            this.m = m;
            this.c = c;
            this.l = l;
        }

        @Override
        public double weightUpto(double x) {
            return m * x + c;
        }

        @Override
        public double length() {
            return l;
        }
    }

    static class Exp implements Log {

        private double l;

        public Exp(double l) {
            this.l = l;
        }

        @Override
        public double weightUpto(double x) {
            if (x < 0) {
                return 0;
            } else if (x > l) {
                return Math.exp(l);
            }
            return Math.exp(x);
        }

        @Override
        public double length() {
            return l;
        }
    }

    static class Power implements Log {

        private double l;
        private double power;

        public Power(double l, double power) {
            this.l = l;
            this.power = power;
        }

        @Override
        public double length() {
            return l;
        }

        @Override
        public double weightUpto(double x) {
            if (x < 0) {
                return 0;
            } else if (x > l) {
                return Math.pow(l, power);
            } else {
                return Math.pow(x, power);
            }
        }
    }

    static class Sine implements Log {

        private double l;

        public Sine(double l) {
            this.l = l;
        }

        @Override
        public double length() {
            return l;
        }

        @Override
        public double weightUpto(double x) {
            if (x < 0) {
                return 0;
            } else if (x > l) {
                return l + Math.sin(l);
            } else {
                return x + Math.sin(x);
            }
        }
    }

}
