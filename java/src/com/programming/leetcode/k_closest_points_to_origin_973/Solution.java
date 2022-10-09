package com.programming.leetcode.k_closest_points_to_origin_973;

import java.util.Arrays;
import java.util.PriorityQueue;

// Problem: https://leetcode.com/problems/k-closest-points-to-origin/
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        return withPriorityQueue(points, k);
    }

    private int[][] withSorting(int[][] points, int k) {
        Arrays.sort(points, (p1, p2) -> getDistance(p1) - getDistance(p2));
        return Arrays.copyOfRange(points, 0, k);
    }

    private int[][] withPriorityQueue(int[][] points, int k) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((p1, p2) -> getDistance(p2) - getDistance(p1));

        for (int[] point : points) {
            queue.add(point);
            if (queue.size() > k) {
                queue.poll();
            }
        }

        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = queue.poll();
        }

        return result;
    }

    private int getDistance(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }
}