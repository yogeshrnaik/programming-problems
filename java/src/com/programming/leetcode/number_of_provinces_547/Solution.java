package com.programming.leetcode.number_of_provinces_547;

// Problem: https://leetcode.com/problems/number-of-provinces/
// Solution: Union Find algorithm (https://www.youtube.com/watch?v=ayW5B2W9hfo)
public class Solution {
    public int findCircleNum(int[][] isConnected) {
        UnionFind unionFind = new UnionFind(isConnected.length);
        int connected = isConnected.length;

        for (int i = 0; i < isConnected.length; i++) {
            for (int j = 0; j < isConnected[0].length; j++) {
                if (i != j && isConnected[i][j] == 1) {
                    connected -= unionFind.union(i, j);
                }
            }
        }
        return connected;
    }
}

class UnionFind {

    private int[] parent;
    private int[] rank;

    public UnionFind(int n) {
        this.parent = new int[n];
        this.rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int x) {
        if (parent[x] != x)
            return find(parent[x]);
        return x;
    }

    public int union(int x, int y) {
        int parentX = find(x);
        int parentY = find(y);

        if (parentX == parentY) return 0; // no union needed as both x and y already have same parent

        if (rank[parentX] > rank[parentY]) {
            // rank of parent of X is higher so we set Y's parent = X's parent
            parent[parentY] = parentX;
            // increase rank of parent of X with same amount as rank of Y's parent
            rank[parentX] += rank[parentY];
        } else {
            parent[parentX] = parentY;
            rank[parentY] += rank[parentX];
        }
        return 1;
    }
}
