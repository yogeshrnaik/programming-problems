package com.programming.leetcode.spiral_matrix_54;

import java.util.ArrayList;
import java.util.List;

// Problem: https://leetcode.com/problems/spiral-matrix/
class Solution {
    private static int VISITED_VALUE = Integer.MAX_VALUE;

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int[] colDir = new int[]{1, 0, -1, 0};
        int[] rowDir = new int[]{0, 1, 0, -1};

        int limit = matrix.length * matrix[0].length;
        int counter = 0;
        int row = 0;
        int col = 0;
        int dir = 0;

        while (counter++ < limit) {
            result.add(matrix[row][col]);
            matrix[row][col] = VISITED_VALUE;
            row += rowDir[dir];
            col += colDir[dir];

            if (isInvalid(row, col, matrix)) {
                row -= rowDir[dir];
                col -= colDir[dir];
                dir = (dir + 1) % 4;
                row += rowDir[dir];
                col += colDir[dir];
            }
        }
        return result;
    }

    private boolean isInvalid(int row, int col, int[][] matrix) {
        return (row < 0 || col < 0 || row >= matrix.length || col >= matrix[0].length || matrix[row][col] == VISITED_VALUE);
    }
}