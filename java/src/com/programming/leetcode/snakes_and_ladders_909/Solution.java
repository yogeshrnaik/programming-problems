package com.programming.leetcode.snakes_and_ladders_909;

import java.util.*;

public class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        Queue<Map.Entry<Integer, Integer>> queue = new LinkedList<>();
        queue.offer(Map.entry(1, 0));
        Set<Integer> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Map.Entry<Integer, Integer> entry = queue.poll();
            int position = entry.getKey();
            int moves = entry.getValue();

            for (int i = 1; i <= 6; i++) {
                int nextPosition = position + i;
                int r = getRow(nextPosition, n);
                int c = getColumn(nextPosition, n);
                if (board[r][c] != -1) {
                    nextPosition = board[r][c];
                }
                if (nextPosition == n * n) {
                    return moves + 1;
                }
                if (!visited.contains(nextPosition)) {
                    visited.add(nextPosition);
                    queue.offer(Map.entry(nextPosition, moves + 1));
                }
            }
        }
        return -1;
    }

    private int getRow(int position, int boardSize) {
        return boardSize - 1 - ((position - 1) / boardSize);
    }

    private int getColumn(int position, int boardSize) {
        int row = getRow(position, boardSize);
        if ((boardSize % 2 == 0 && row % 2 == 0) || (boardSize % 2 != 0 && row % 2 != 0)) {
            return boardSize - 1 - ((position - 1) % boardSize);
        }
        return (position - 1) % boardSize;
    }
}
