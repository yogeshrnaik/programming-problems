package com.programming.leetcode.lowest_common_ancestor_236;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

// Problem: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
class Solution {
    TreeNode result = null;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        findLCA(root, p, q);
//        return result;
        if (root == null) return null;
        if (root.val == p.val || root.val == q.val) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root; // split happens at this node

        return (left != null) ? left : right;

    }

    private boolean findLCA(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return false;
        boolean left = findLCA(root.left, p, q);
        boolean right = findLCA(root.right, p, q);
        boolean curr = root.val == p.val || root.val == q.val;

        if ((curr && left) || (curr && right) || (left && right)) result = root;
        return left || curr || right;
    }
}