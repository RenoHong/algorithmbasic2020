package class03.practice02;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DeleteGivenValue {

    static List<Integer> ints = new LinkedList<>();

    private static boolean validate(Node head, int num) {


        for (int i = ints.size() - 1; i >= 0; i--) {
            if (ints.get(i) == num) {
                ints.remove(i);
            }
        }

        if ((head == null && !ints.isEmpty()) ||
                head != null && ints.isEmpty())
            return false;


        Node current = head;
        for (int i = 0; i < ints.size(); i++) {
            if (current == null)
                return false;
            if (current.value != ints.get(i))
                return false;
            current = current.next;
        }
        if (current != null) return false;

        return true;
    }

    private static Node randomNodeList(int maxLen, int maxValue) {
        ints.clear();
        Random rand = new Random();
        int len = rand.nextInt(maxLen) + 1;
        Node head = new Node(rand.nextInt(maxValue));
        ints.add(head.value);
        Node dummy = new Node(0);
        dummy.next = head;
        len--;
        while (len-- > 0) {
            head.next = new Node(rand.nextInt(maxValue));
            head = head.next;
            ints.add(head.value);
        }

        return dummy.next;
    }

    private static int randomValue2Delete() {
        Random random = new Random();
        return ints.get(random.nextInt(ints.size()));
    }

    public static Node deleteGivenValue(Node head, int num) {
        if (head == null)
            return head;

        while (head != null) {
            if (head.value != num)
                break;
            head = head.next;
        }

        Node current = head;
        Node previous = head;
        while (current != null) {
            if (current.value == num) {
                previous.next = current.next;
            } else {
                previous = current;
            }
            current = current.next;
        }
        return head;

    }

    public static void main(String[] args) {
        int testTimes = 100;
        int maxValue = 1000;
        int maxLen = 10;
        System.out.println(">>>>");
        for (int i = 0; i < testTimes; i++) {
            Node head = randomNodeList(maxLen, maxValue);
            int num = randomValue2Delete();
            Node deletedNode = deleteGivenValue(head, num);
            if (!validate(deletedNode, num)) {
                System.out.println("Failed!");
                break;
            }
        }
        System.out.println("<<<<");
    }

    public static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }
}
