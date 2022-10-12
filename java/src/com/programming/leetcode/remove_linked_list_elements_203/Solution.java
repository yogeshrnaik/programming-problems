package com.programming.leetcode.remove_linked_list_elements_203;

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class Solution {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        ListNode curr = head;
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;

        while (curr != null) {
            if (curr.val == val) {
                // remove curr
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }

        return dummy.next;
    }
}