package com.programming.leetcode.delete_node_in_linked_list_237;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDeleteNodeInLinkedList {
    
    @Test
    public void testDeleteNode() {
        ListNode head = new ListNode(7);
        ListNode node1 = new ListNode(8);
        ListNode node2 = new ListNode(9);
        head.next = node1;
        node1.next = node2;
        new Solution().deleteNode(node1);

        Assert.assertEquals(Arrays.asList(7, 9), asList(head));
        
    }

    private List<Integer> asList(ListNode curr) {
        List<Integer> result = new ArrayList<>();
        while (curr != null) {
            result.add(curr.val);
            curr = curr.next;
        }
        return result;
    }
}
