package com.programming.qualys.problem2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class Result {

    /**
     * Complete the 'collision' function below. <br/>
     * The function is expected to return an INTEGER. <br/>
     * The function accepts following parameters: <br/>
     * 1. INTEGER_ARRAY speed <br/>
     * 2. INTEGER pos
     */

    public static int collision(List<Integer> speed, int pos) {
        // Write your code here
        int collisions = 0;
        int speedAtPos = speed.get(pos);

        for (int i = pos - 1; i >= 0; i--) {
            if (speed.get(i) >= speedAtPos) {
                collisions++;
            }
        }

        for (int i = pos + 1; i < speed.size(); i++) {
            if (speed.get(i) < speedAtPos) {
                speedAtPos = speed.get(i);
                collisions++;
            }
        }
        return collisions;
    }

}

public class Solution {

    public static void main(String[] args) throws IOException {
        // System.out.println(Result.collision(Arrays.asList(8, 3, 6, 3, 2, 2, 4, 8, 1, 6), 7));

        System.out.println(Result.collision(Arrays.asList(1, 3, 7, 4, 6, 4), 3));
    }
}
