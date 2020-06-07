package io.whaley._001_linkedlist;

/**
 * https://leetcode.com/problems/delete-node-in-a-linked-list/
 * 在无法遍历链表的时候（不知道头节点）删除指定元素，假设删除元素 A，
 * 将元素 A 的后一个元素的值替换到 A 元素的值，
 * 将 A.next 指向 A.next.next
 */
public class _237_DeleteNodeLinkedList {

}

class LinkedList {

    LinkedList() {

    }

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
