package io.whaley._001_linkedlist;

/**
 * https://leetcode.com/problems/reverse-linked-list/
 * 206. Reverse Linked List
 * 反转链表
 * Input: 1->2->3->4->5->NULL
 * Output: 5->4->3->2->1->NULL
 * 思路一：定义一个新的头节点，遍历链表，每次将原素插入新的链表的第一个位置
 */
public class _206_ReverseLinkedList {
    public static void main(String[] args) {
        Solution206 solution = new Solution206();
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

        ListNode node = solution.reverseList(node5);

        System.out.println(node);
    }
}

class Solution206 {
    /**
     * 使用递规实现
     * @param head
     * @return
     */
    public ListNode reverseList1(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList1(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public ListNode reverseList(ListNode head) {
        // 定义一个新的头节点
        ListNode newHead = null;
        // 当前遍历到的节点
        ListNode curr = head;
        while (curr != null) {
            // 指向当前节点的下一个节点
            ListNode temp = curr.next;
            // 将当前节点指向新链表的第一个节点
            curr.next = newHead;
            // 将新链表的头指针指向第一个节点
            newHead = curr;
            // 将原来链表的头节点指向
            curr = temp;
        }

        return newHead;
    }
}