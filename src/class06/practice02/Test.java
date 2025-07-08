package class06.practice02;

import java.util.PriorityQueue;

public class Test {

    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b)-> -(a-b) );
        queue.add(10);
        queue.add(2);
        queue.add(34);
        queue.add(0);

        System.out.println(queue.peek());
    }
}
