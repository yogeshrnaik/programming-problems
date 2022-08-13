package com.programming.qualys.problem3;

import java.io.IOException;
import java.util.Arrays;

class Result {

    /*
     * Complete the 'numberOfWays' function below. The function is expected to return an INTEGER. The function accepts INTEGER n as
     * parameter.
     */

    public static int numberOfWays(int n) {
        int result[] = new int[n + 1], i;
        Arrays.fill(result, 0);
        result[0] = 1;

        // the picked move
        for (i = 1; i <= n; i++)
            result[i] += result[i - 1];
        for (i = 3; i <= n; i++)
            result[i] += result[i - 3];
        return result[n] % 1000000007;
    }

}

public class Solution {

    public static void main(String[] args) throws IOException {
        System.out.println(Result.numberOfWays(7));
    }
}
