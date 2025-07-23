package class03.practice02;

public class TwoPointers {

    public static Node MidOrDown(Node root) {
        if (root == null || root.next == null)
            return root;

        Node slow = root;
        Node fast = root;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public static Node MidOrUp(Node root) {
        if (root == null || root.next == null)
            return root;
        Node slow = root;
        Node fast = root;
        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        Node node = new Node(1);
        node.next = new Node(2);
        node.next.next = new Node(3);
        node.next.next.next = new Node(4);
        node.next.next.next.next = new Node(5);
        node.next.next.next.next.next = new Node(6);

        Node res = MidOrDown(node);
        Node res2 = MidOrUp(node);
        System.out.println(res.value);
        System.out.println(res2.value);
    }

    public static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

}
