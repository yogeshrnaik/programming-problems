package com.programming.qualys.sampletest.problem2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    // Complete the oddNumbers function below.
    static List<Integer> oddNumbers(int l, int r) {
        return IntStream.range(l, r + 1)
            .filter(i -> i % 2 != 0)
            .boxed().collect(Collectors.toList());
    }
}
