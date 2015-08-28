package com.politics;
import java.io.*;
import java.util.Arrays;

public class CandidateCode
{
    public static String partiescompare(int[] input1,int[] input2)
    {
        int[] party1 = input1;
        int[] party2 = input2;

        if (checkIfInvalid(party1, party2)) {
            return "Invalid";
        }

        if (checkIfEqual(party1, party2)) {
            return "Equal";
        }

        return "Unequal";
    }
    private static boolean checkIfEqual(int[] party1, int[] party2) {
        // sort both arrays and compare if they are same
        Arrays.sort(party1);
        Arrays.sort(party2);

        for (int i=0; i < party1.length; i++) {
            if (party1[i] != party2[i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkIfInvalid(int[] party1, int[] party2) {
        if (party1 == null || party2 == null || party1.length != party2.length) {
            return true;
        }

        for (int i=0; i<party1.length; i++) {
            if (party1[i] < 0 || party2[i] < 0) {
                return true;
            }
        }

        return false;
    }

}
