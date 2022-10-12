package com.programming.leetcode.delete_node_in_linked_list_237;

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

// Problem: https://leetcode.com/problems/delete-node-in-a-linked-list/
public class Solution {
    public void deleteNode(ListNode node) {
        ListNode prev = null;
        while (node != null) {
            if (node.next != null)
                node.val = node.next.val;
            else
                prev.next = null;
            prev = node;
            node = node.next;
        }
    }
}