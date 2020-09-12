package com.programming.booking.problem3;

import java.util.Scanner;

public class Solution {

    // Complete the find_all_possible_teams function below.
    static void find_all_possible_teams() {
        try (Scanner s = new Scanner(System.in)) {
            String team = s.nextLine();

            for (int k=1; k <= 26; k++) {
                print_combination(team, k, 0, team.length(), "");
            }
        }
    }

    static void loop(String team, String startChar, int start, int end) {
        for (int i = start; i < end; i++) {
            System.out.println(startChar + "" + team.charAt(i));
        }
    }

    static void print_combination(String team, int k, int start, int end, String startChar) {
        if (k == 1) {
            for (int i = start; i < end; i++) {
                System.out.println(startChar + "" + team.charAt(i));
            }
        } else {
            for (int i = start; i < end - k + 1; i++) {
                print_combination(team, k - 1, i + 1, end, startChar + "" + team.charAt(i));
            }
        }
    }


    static void print_all_teams(String team) {
        loop(team, "",0, team.length());

        for (int i = 0; i < team.length() - 1; i++) {
            loop(team, team.charAt(i)+"" ,i+1, team.length());
        }

        for (int i = 0; i < team.length() - 2; i++) {
            for (int j = i + 1; j < team.length() - 1; j++) {
                loop(team, team.charAt(i) + "" + team.charAt(j) ,j+1, team.length());
            }
        }

        for (int i = 0; i < team.length() - 3; i++) {
            for (int j = i + 1; j < team.length() - 2; j++) {
                for (int k = j + 1; k < team.length() - 1; k++) {
                    for (int l = k + 1; l < team.length(); l++) {
                        System.out.println(team.charAt(i) + "" + team.charAt(j) + "" + team.charAt(k) + "" + team.charAt(l));
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        find_all_possible_teams();

    }
}
