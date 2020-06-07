package io.whaley._001_linkedlist;

public class _141_LinkedListCycle {
    Solution141 solution = new Solution141();

}

class Solution141 {
    // 快慢指针
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while (slow != null && fast.next != null) {
            if (slow.val == fast.val) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;

        }
        return false;
    }
}