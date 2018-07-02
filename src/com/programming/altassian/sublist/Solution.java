package com.programming.altassian.sublist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String args[]) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        try (BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in))) {
            List<String> list1 = readList(scanner);
            List<String> list2 = readList(scanner);
            System.out.println(find(list1, list2));
        }
    }

    private static int find(List<String> list1, List<String> list2) {
        String firstItemFromList2 = list2.get(0);
        int indexOfFirstItemInList1 = list1.indexOf(firstItemFromList2);
        if (indexOfFirstItemInList1 == -1) {
            return -1;
        }

        for (int i = 0; i < list2.size(); i++) {
            if (!list1.get(i + indexOfFirstItemInList1).equals(list2.get(i)))
                return -1;
        }

        return indexOfFirstItemInList1;
    }

    private static List<String> readList(BufferedReader scanner) throws NumberFormatException, IOException {
        List<String> list = new ArrayList<>();

        int length = Integer.parseInt(scanner.readLine());

        for (int i = 0; i < length; i++) {
            list.add(scanner.readLine());
        }

        return list;
    }
}