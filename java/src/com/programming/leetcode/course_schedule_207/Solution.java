package com.programming.leetcode.course_schedule_207;

import java.util.*;

// Problem: https://leetcode.com/problems/course-schedule/
public class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> adj = createAdjacencyList(prerequisites);

        Set<Integer> visited = new HashSet<>();

        for (int i = 0; i < numCourses; i++) {
            if (!isPossibleToTake(i, visited, adj)) {
                return false;
            }
        }
        return true;
    }

    private Map<Integer, List<Integer>> createAdjacencyList(int[][] prerequisites) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < prerequisites.length; i++) {
            int course = prerequisites[i][0];
            int mustCourse = prerequisites[i][1];
            List<Integer> mustCourses = adj.getOrDefault(course, new ArrayList<Integer>());
            mustCourses.add(mustCourse);
            adj.put(course, mustCourses);
        }
        return adj;
    }

    private boolean isPossibleToTake(int course, Set<Integer> visited, Map<Integer, List<Integer>> adj) {
        if (visited.contains(course)) return false;
        if (adj.get(course) == null || adj.get(course).isEmpty()) return true;

        visited.add(course);
        for (int mustCourse : adj.get(course)) {
            if (!isPossibleToTake(mustCourse, visited, adj)) return false;
        }
        visited.remove(course);
        adj.put(course, new ArrayList<Integer>());

        return true;
    }
}