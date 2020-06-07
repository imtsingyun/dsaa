package io.whaley._001_linkedlist;

/**
 * https://leetcode.com/problems/delete-node-in-a-linked-list/
 * 在无法遍历链表的时候（不知道头节点）删除指定元素，假设删除元素 A，
 * 将元素 A 的后一个元素的值替换到 A 元素的值，
 * 将 A.next 指向 A.next.next
 */
public class _237_DeleteNodeLinkedList {
    public static void main(String[] args) {
        Solution237 solution = new Solution237();
        ListNode node5 = new ListNode(5);
        ListNode node4 = new ListNode(4);
        ListNode node3 = new ListNode(3);
        ListNode node2 = new ListNode(2);
        ListNode node1 = new ListNode(1);
        node5.next = node4;
        node4.next = node3;
        node3.next = node2;
        node2.next = node1;
        System.out.println(node5);

        solution.deleteNode(node5);

        System.out.println(node5);
    }
}

class Solution237 {
    public void deleteNode(ListNode node) {
        if (node.next == null) {
            node = null;
        } else {
            node.val = node.next.val;
            node.next = node.next.next;
        }
    }
}

