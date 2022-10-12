package com.programming.leetcode.remove_linked_list_elements_203;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRemoveLinkedListElements {

    @Test
    public void testRemoveLinkedListElements_sample1() {
        ListNode head = new ListNode(7, new ListNode(7, new ListNode(8)));
        ListNode newHead = new Solution().removeElements(head, 8);

        Assert.assertEquals(Arrays.asList(7, 7), asList(newHead));

    }

    @Test
    public void testRemoveLinkedListElements_sample2() {
        ListNode head = new ListNode(7, new ListNode(7, new ListNode(7)));
        ListNode newHead = new Solution().removeElements(head, 7);
        Assert.assertNull(newHead);

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
