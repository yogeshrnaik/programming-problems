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
        if (root == null) return null;
        if (root.val == p.val || root.val == q.val) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // if both left and right are not null,
        // this means p and q lies in left and right part of the tree from current root node
        if (left != null && right != null) return root;

        // left not null means, both p and q are part of left sub-tree
        return (left != null) ? left : right;

        /* Alternate approach */
        // findLCA(root, p, q);
        // return result;

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