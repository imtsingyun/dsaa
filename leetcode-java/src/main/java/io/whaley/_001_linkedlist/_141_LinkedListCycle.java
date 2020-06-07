package io.whaley._001_linkedlist;

/**
 * https://leetcode.com/problems/linked-list-cycle/submissions/
 * 141. Linked List Cycle
 */
public class _141_LinkedListCycle {
    Solution141 solution = new Solution141();
}

class Solution141 {
    // 快慢指针
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {
            if (slow.val == fast.val) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }
}