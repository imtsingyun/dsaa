package io.whaley._001_linkedlist;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * https://leetcode.com/problems/delete-node-in-a-linked-list/
 */
public class _237_DeleteNodeLinkedList {

}

class LinkedList {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

    }

    public void deleteNode(ListNode node) {
        if (node.next == null) {
            node = null;
        } else {
            node.val = node.next.val;
            node.next = node.next.next;
        }
    }

}
