package com.programming.leetcode.lowest_common_ancestor_bst_235;

// Definition for a binary tree node.
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}


// Problem: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;

        while (curr != null) {
            if (p.val > curr.val && q.val > curr.val) curr = curr.right;
            else if (p.val < curr.val && q.val < curr.val) curr = curr.left;
            else return curr;
        }
        return curr;
    }
}