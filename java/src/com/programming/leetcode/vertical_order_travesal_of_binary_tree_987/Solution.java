package com.programming.leetcode.vertical_order_travesal_of_binary_tree_987;

import java.util.*;


// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

// Problem: https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
public class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
//        return solve(root);
        return WithPriorityQueue.verticalTraversal(root);
    }

    private List<List<Integer>> solve(TreeNode root) {
        Map<Integer, List<Integer>> vertical = new TreeMap<>();
        List<List<Integer>> result = new ArrayList<>();
        bfsSolution(root, vertical);
        for (List<Integer> colValues : vertical.values()) {
            result.add(colValues);
        }
        return result;
    }

    private void bfsSolution(TreeNode root, Map<Integer, List<Integer>> vertical) {
        Queue<Map.Entry<Integer, TreeNode>> queue = new LinkedList<>();
        queue.offer(Map.entry(0, root));

        while (!queue.isEmpty()) {
            Map.Entry<Integer, TreeNode> entry = queue.poll();
            int col = entry.getKey();
            TreeNode node = entry.getValue();
            List<Integer> colValues = vertical.getOrDefault(col, new ArrayList<>());
            colValues.add(node.val);
            vertical.put(col, colValues);

            if (node.left != null) {
                queue.offer(Map.entry(col - 1, node.left));
            }
            if (node.right != null) {
                queue.offer(Map.entry(col + 1, node.right));
            }
        }
    }

    private void dfs(TreeNode curr, int col, Map<Integer, List<Integer>> vertical) {
        if (curr == null) {
            return;
        }
        List<Integer> colValues = vertical.getOrDefault(col, new ArrayList<>());
        colValues.add(curr.val);
        vertical.put(col, colValues);
        dfs(curr.left, col - 1, vertical);
        dfs(curr.right, col + 1, vertical);
    }
}

class WithPriorityQueue {
    private static TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> colToRowValues;

    public static List<List<Integer>> verticalTraversal(TreeNode root) {
        colToRowValues = new TreeMap<>();
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        dfs(0, 0, root);
        for (TreeMap<Integer, PriorityQueue<Integer>> value : colToRowValues.values()) {
            List<Integer> temp = new ArrayList<>();
            for (PriorityQueue<Integer> pq : value.values()) {
                while (!pq.isEmpty()) {
                    temp.add(pq.poll());
                }
            }
            result.add(temp);
        }
        return result;
    }

    private static void dfs(int col, int row, TreeNode node) {
        TreeMap<Integer, PriorityQueue<Integer>> rowToValues = colToRowValues.containsKey(col) ? colToRowValues.get(col) : new TreeMap<Integer, PriorityQueue<Integer>>(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer n1, Integer n2) {
                        return n2 - n1;
                    }
                }
        );
        PriorityQueue<Integer> rowValues = rowToValues.containsKey(row) ? rowToValues.get(row) : new PriorityQueue<Integer>();
        rowValues.offer(node.val);
        rowToValues.put(row, rowValues);
        colToRowValues.put(col, rowToValues);
        if (node.left != null) dfs(col - 1, row - 1, node.left);
        if (node.right != null) dfs(col + 1, row - 1, node.right);
    }
}