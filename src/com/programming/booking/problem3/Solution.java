package com.programming.booking.problem3;

import java.util.Scanner;

public class Solution {

    // Complete the find_all_possible_teams function below.
    static void find_all_possible_teams() {
        try (Scanner s = new Scanner(System.in)) {
            String team = s.nextLine();
            print_all_teams(team);
        }
    }


    static void print_all_teams(String team) {
        for (int i = 0; i < team.length(); i++) {
            System.out.println(team.charAt(i));
        }

        for (int i = 0; i < team.length() - 1; i++) {
            for (int j = i + 1; j < team.length(); j++) {
                System.out.println(team.charAt(i) + "" + team.charAt(j));
            }
        }

        for (int i = 0; i < team.length() - 2; i++) {
            for (int j = i + 1; j < team.length() - 1; j++) {
                for (int k = j + 1; k < team.length(); k++) {
                    System.out.println(team.charAt(i) + "" + team.charAt(j) + "" + team.charAt(k));
                }
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
