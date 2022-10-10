package com.programming.leetcode.vertical_order_travesal_of_binary_tree_987;

import org.junit.Test;

import java.util.List;

public class TestVerticalOrderTraversal {

    @Test
    public void testVerticalOrderTraversal() {
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode root = new TreeNode(1, node1, node2);

        List<List<Integer>> result = new Solution().verticalTraversal(root);
        System.out.println(result);
    }

}
