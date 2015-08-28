package com.gcd;


import java.io.*;
public class CandidateCode
{
    public static int[] coins_value(int[] input1)
    {
        if (input1 == null || input1.length != 2 || input1[0] < 0 || input1[1] < 0) {
            return new int[] {0, 0};
        }

        int[] result = gcd(input1[0], input1[1]);

        return new int[] {result[1], result[2]};
    }

    public static int[] gcd(int a, int b) {
        if (b == 0)
            return new int[] { a, 1, 0 };

        int[] vals = gcd(b, a % b);
        int d = vals[0];
        int x = vals[2];
        int y = vals[1] - (a / b) * vals[2];
        return new int[] { d, x, y };
    }
}