package org.mindidea._001_linkedlist;

/**
 * https://leetcode.com/problems/remove-linked-list-elements/
 * 203. Remove Linked List Elements
 *
 * Input:  1->2->6->3->4->5->6, val = 6
 * Output: 1->2->3->4->5
 */
public class _203_RemoveLinkedListElements {
    public static void main(String[] args) {
        Solution203 solution = new Solution203();
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(2);
        ListNode node4 = new ListNode(1);
//        ListNode node5 = new ListNode(4);
//        ListNode node6 = new ListNode(5);
//        ListNode node7 = new ListNode(6);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
//        node4.next = node5;
//        node5.next = node6;
//        node6.next = node7;

        System.out.println(node1);

        ListNode node = solution.removeElements(node1, 2);

        System.out.println(node);
    }
}

class Solution203 {
    public ListNode removeElements1(ListNode head, int val) {
        // 构造一个临时头节点，用来记录 prev 结点
        ListNode tempHead = new ListNode(-1);
        tempHead.next = head;
        // 从临时节点开始遍历
        ListNode curr = tempHead;
        while (curr.next != null) {
            if (curr.next.val == val) {
                ListNode next = curr.next;
                curr.next = next.next;
                next.next = null;
            } else {
                curr = curr.next;
            }
        }
        return tempHead.next;
    }

    public ListNode removeElements(ListNode head, int val) {
        ListNode p = head;
        while (p != null) {
            // 判断当前结点（第一个结点）
            if (p.val == val) {
                ListNode curr = p;
                p = curr.next;
                curr.next = null;
                // 第一个结点被删除后，头结点需要往后移
                head = p;
            } else {
                // 判断当前结点的 next 结点是否需要删除
                if (p.next != null && val == p.next.val) {
                    ListNode nextNode = p.next;
                    p.next = p.next.next;
                    nextNode.next = null;
                } else {
                    // 不要忘了将 p 指针往后移
                    p = p.next;
                }
            }
        }
        return head;
    }
}