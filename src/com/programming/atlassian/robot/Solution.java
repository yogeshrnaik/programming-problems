package com.programming.atlassian.robot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Solution {

    /*
     * Complete the compute function below.
     */
    static String compute(String instructions) {
        /*
         * Write your code here.
         */
        Robot robo = new Robot();
        for (int i = 0; i < instructions.length(); i++) {
            robo.processInstruction(String.valueOf(instructions.charAt(i)));
        }

        return robo.getStack();
    }

    static class Robot {

        boolean pickedUpItem = false;
        int[] stack = new int[10];
        int currIndex = 0;

        public void processInstruction(String instruction) {
            if ("P".equals(instruction)) {
                if (!pickedUpItem) {
                    // if robot has not yet picked up item
                    pickedUpItem = true;
                }
                currIndex = 0;
            } else if ("M".equals(instruction)) {
                if (currIndex != 9) {
                    currIndex++;
                }
            } else if ("L".equals(instruction)) {
                if (!pickedUpItem) {
                    // do nothing as there is no block to lower
                } else if (stack[currIndex] == 15) {
                    // do nothing as stack is full at current index
                } else {
                    stack[currIndex] = stack[currIndex] + 1;
                    pickedUpItem = false;
                }
            }
        }

        public String getStack() {
            String strStack = "";
            for (int i = 0; i < stack.length; i++) {
                strStack = strStack + Integer.toHexString(stack[i]).toUpperCase();
            }
            return strStack;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String instructions = scanner.nextLine();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        String res = compute(instructions);

        bufferedWriter.write(res);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
