package class03.practice02;

import java.util.Random;
import java.util.Stack;

public class ReverseList {

    public static DoubleNode reverseDoubleLinkedList(DoubleNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        DoubleNode pre = null;
        while (head != null) {
            DoubleNode next = head.next;
            head.next = pre;
            head.pre = next;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static Node reverseLinkedList(Node head) {
        if (head == null || head.next == null)
            return head;
        Node pre = null;
        while (head != null) {
            Node next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    private static void printLinkedList(Node head) {
        while (head != null) {
            System.out.printf(",%d", head.value);
            head = head.next;
        }
    }

    private static void printLinkedList(DoubleNode head) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        DoubleNode current = head;
        while (current != null) {
            sb.append(current.value).append(',');
            current = current.next;
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        System.out.println(sb);
    }

    private static boolean validateReversedNodes(Node original, Node reversed) {
        Stack<Integer> stack = new Stack<>();
        Node head = original;
        while (head != null) {
            stack.push(head.value);
            head = head.next;
        }

        if (reversed != null && stack.isEmpty())
            return false;

        head = reversed;
        while (head != null) {
            if (head.value != stack.pop())
                return false;
            head = head.next;
        }

        if (stack.isEmpty())
            return true;
        else
            return false;
    }

    private static Node copyLinkedList(Node original) {
        Node dummy = new Node(0);
        Node tail = dummy;
        Node current = original;
        while (current != null) {
            tail.next = new Node(current.value);
            current = current.next;
            tail = tail.next;
        }
        return dummy.next;
    }

    private static DoubleNode copyLinkedList(DoubleNode origin) {
        DoubleNode dummy = new DoubleNode(0);
        DoubleNode current = dummy;
        DoubleNode next = origin;
        while (next != null) {
            current.next = new DoubleNode(next.value);
            next = next.next;
            DoubleNode pre = current;
            current = current.next;
            current.pre = pre;
        }
        return dummy.next;
    }

//    private static Node copyLinkedList(Node ori) {
//        if (ori == null )
//            return null ;
//        Node head, next;
//        Node cur = ori;
//        head = next = new Node(ori.value) ;
//        while (cur.next != null) {
//            next.next = new Node(cur.next.value) ;
//            cur = cur.next ;
//            next = next.next ;
//        }
//        return head ;
//    }

    private static boolean validateDoubleLinkedList(DoubleNode origin, DoubleNode reversed) {
        if (origin == null && reversed == null) return true;
        if (origin == null || reversed == null) return false;

        DoubleNode curOrigin = origin;
        DoubleNode curReversed = reversed;
        while (curReversed.next != null) {
            curReversed = curReversed.next;
        }

        while (curOrigin != null && curReversed != null) {
            if (curReversed.value != curOrigin.value)
                return false;
            curReversed = curReversed.pre;
            curOrigin = curOrigin.next;
        }
        return curReversed == null && curOrigin == null;
    }

    private static DoubleNode randomDoublelist(int maxLen, int maxValue) {
        Random rand = new Random();
        int len = rand.nextInt(maxLen + 1);
        DoubleNode dummy = new DoubleNode(0);
        DoubleNode current = dummy;
        while (len-- > 0) {
            DoubleNode next = new DoubleNode(rand.nextInt(maxValue + 1));
            current.next = next;
            next.pre = current;
            current = current.next;
        }
        if (dummy.next != null)
            dummy.next.pre = null;
        return dummy.next;
    }

    private static Node randomLinkedList(int maxNodes, int maxValue) {
        Random rand = new Random();
        int nodes = rand.nextInt(maxNodes - 1) + 1;
        Node head = new Node(rand.nextInt(maxValue));
        Node cur = head;
        for (int i = 0; i < nodes - 1; i++) {
            Node n = new Node(rand.nextInt(maxValue));
            cur.next = n;
            cur = n;
        }
        return head;
    }

    public static void main(String[] args) {
        int maxNodes = 8;
        int maxValue = 1000;
        int testTimes = 100;
//        Node node = randomLinkedList(maxNodes, maxValue);
//        Node copied = copyLinkedList(node);
//        Node reversed = reverseLinkedList(node);
//        System.out.println(">>>");
//        for (int i = 0; i < testTimes; i++) {
//            if (!validateReversedNodes(copied, reversed)) {
//                printLinkedList(node);
//                System.out.println("\n==========");
//                printLinkedList(reversed);
//                System.out.println("Ops!");
//                break;
//            }
//        }
//        System.out.println("<<<");
//
//        DoubleNode doubleNode = randomDoublelist(maxNodes, maxValue) ;

        System.out.println(">>>");
        for (int i = 0; i < testTimes; i++) {
            DoubleNode doubleNode = randomDoublelist(maxNodes, maxValue);
            DoubleNode cpDoubleNode = copyLinkedList(doubleNode);
            DoubleNode reversedDoubleList = reverseDoubleLinkedList(doubleNode);
            if (!validateDoubleLinkedList(cpDoubleNode, reversedDoubleList)) {
                System.out.println("Opps!");
                printLinkedList(cpDoubleNode);
                printLinkedList(reversedDoubleList);
            }
        }
        System.out.println("<<<");
    }

    public static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class DoubleNode {
        int value;
        DoubleNode pre, next;

        public DoubleNode(int value) {
            this.value = value;
        }
    }


}
