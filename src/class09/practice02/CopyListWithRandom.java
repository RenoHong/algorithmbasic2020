package class09.practice02;

import java.util.HashMap;
import java.util.Map;

public class CopyListWithRandom {

    public static Node copyRandomList1(Node head) {
        Map<Node, Node> nodeMap = new HashMap<>();

        Node current = head;
        while (current != null) {
            nodeMap.put(current, new Node(current.value));
            current = current.next;
        }
        current = head;
        while (current != null) {
            nodeMap.get(current).next = nodeMap.get(current.next);
            nodeMap.get(current).random = nodeMap.get(current.random);
            current = current.next;
        }
        return nodeMap.get(head);

    }

    public static Node copyRandomList2(Node head) {
        if (head == null)
            return null;

        //Create new nodes ;
        Node current = head;
        while (current != null) {
            Node next = current.next;
            Node node = new Node(current.value);
            current.next = node;
            node.next = next;
            current = next;
        }

        //Copy random nodes
        current = head;
        while (current != null) {
            if (current.random != null) {
                current.next.random = current.random.next;
            }
            current = current.next.next;
        }

        //Split new nodes;
        current = head;
        Node res = head.next;
        Node copy = res;
        while (current != null) {
            Node next = current.next.next;
            copy = current.next;
            current.next = next;  // Restore original list
            current = next;
            copy.next = current == null ? null : current.next; // Set copy list
        }

        return res;

    }

    public static class Node {
        public int value;
        public Node next, random;

        public Node(int value) {
            this.value = value;
        }
    }


}
