package class06.practice02;

public class FindFirstIntersectNode {

    public static Node getFirstIntersectNode(Node head1, Node head2) {
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);

        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        } else if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;
    }

    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        int n = 0;
        Node current1 = head1;
        Node current2 = head2;

        // Step 1: Calculate length difference and find end nodes
        while (current1.next != null) {
            n++;
            current1 = current1.next;
        }
        while (current2.next != null) {
            n--;
            current2 = current2.next;
        }

        // Step 2: Critical check - if end nodes are different, lists don't intersect
        if (current1 != current2) {
            return null;  // This guarantees intersection exists if we continue
        }

        // Step 3: Since we verified intersection exists, both pointers will meet before reaching null
        current1 = n > 0 ? head1 : head2;
        current2 = current1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while (n != 0) {
            current1 = current1.next;
            n--;
        }

        // Step 4: This loop is guaranteed to terminate with current1 == current2
        // because we already verified the lists intersect
        while (current1 != current2) {  // No null check needed
            current1 = current1.next;
            current2 = current2.next;
        }
        return current1;

    }

    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node fast = head; // Original approach: both start from head
        Node slow = head; // This is also correct

        // Phase 1: Find meeting point
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }

        // Check if no loop found
        if (fast == null || fast.next == null) {
            return null;
        }

        // Phase 2: Find loop entrance
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        if (loop1 == loop2) {
            int n = 0;
            Node current1 = head1;
            Node current2 = head2;
            while (current1 != loop1) {
                n++;
                current1 = current1.next;
            }
            while (current2 != loop2) {
                n--;
                current2 = current2.next;
            }
            current1 = n > 0 ? head1 : head2;
            current2 = current1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                current1 = current1.next;
                n--;
            }
            while (current1 != current2) {
                current1 = current1.next;
                current2 = current2.next;
            }
            return current1;
        } else {
            Node current1 = loop1.next;
            while (current1 != loop1) { // Fix: should be loop1, not loop2
                if (current1 == loop2) {
                    return loop1; // Fix: return loop1 or loop2, not null
                }
                current1 = current1.next;
            }
            return null;
        }
    }

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

}
