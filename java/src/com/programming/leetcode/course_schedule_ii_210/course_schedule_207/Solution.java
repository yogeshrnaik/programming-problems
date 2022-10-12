package com.programming.leetcode.course_schedule_ii_210.course_schedule_207;

import java.util.*;

// Problem: https://leetcode.com/problems/course-schedule-ii/
public class Solution {

    private Set<Integer> visited = new HashSet<>();
    private Set<Integer> cycle = new HashSet<>();

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, List<Integer>> adj = createAdjacencyList(numCourses, prerequisites);

        for (int i = 0; i < numCourses; i++) {
            if (!isPossibleToTake(i, adj, result)) return new int[0];
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    private boolean isPossibleToTake(int course, Map<Integer, List<Integer>> adj, List<Integer> result) {
        if (cycle.contains(course)) return false;
        if (visited.contains(course)) return true; // already part of result

        cycle.add(course);
        for (int mustCourse : adj.get(course)) {
            if (!isPossibleToTake(mustCourse, adj, result)) return false;
        }
        cycle.remove(course);
        visited.add(course);
        result.add(course);
        adj.put(course, new ArrayList<Integer>());
        return true;
    }


    private Map<Integer, List<Integer>> createAdjacencyList(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> adj = new HashMap<>();

        // add default empty list for each course
        for (int i = 0; i < numCourses; i++) {
            adj.put(i, new ArrayList<Integer>());
        }

        // populate pre-requisite courses in adjacency list for each course
        for (int i = 0; i < prerequisites.length; i++) {
            int course = prerequisites[i][0];
            int mustCourse = prerequisites[i][1];
            adj.get(course).add(mustCourse);
        }
        return adj;
    }
}