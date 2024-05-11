/*
Given a singly-linked list containing (n) items, rearrange the items uniformly at random.
Your algorithm should consume a logarithmic (or constant) amount of extra memory and run in time proportional to
(nlogn) in the worst case.
 */

import java.util.Random;

public class ShuffleLinkedList {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    public ListNode shuffle(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // Split the list into two halves
        ListNode mid = findMiddle(head);
        ListNode secondHalf = mid.next;
        mid.next = null;

        // Recursively shuffle both halves
        ListNode shuffledFirstHalf = shuffle(head);
        ListNode shuffledSecondHalf = shuffle(secondHalf);

        // Merge the shuffled halves
        return merge(shuffledFirstHalf, shuffledSecondHalf);
    }

    private ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode merge(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;

        Random random = new Random();

        while (list1 != null && list2 != null) {
            if (random.nextBoolean()) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        if (list1 != null) {
            current.next = list1;
        }
        if (list2 != null) {
            current.next = list2;
        }

        return dummy.next;
    }

    // Utility function to print the linked list
    private void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Example usage
        ShuffleLinkedList shuffler = new ShuffleLinkedList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.println("Original list:");
        shuffler.printList(head);

        head = shuffler.shuffle(head);

        System.out.println("Shuffled list:");
        shuffler.printList(head);
    }
}
